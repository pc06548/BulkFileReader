package services

import models._

object QueryService {

  def getAirportsAndRunwaysByCountryName(name: String, countries: List[Country], airports: List[Airport], runways: List[Runway]): CountriesAirportsRunwaysDetails = {
    val matchedCountries = getCountryByName(name, countries).distinct
    ReportService.getCountriesWithHighestAirports(20, countries, airports)
    ReportService.getCountriesWithLowestAirports(20, countries, airports)
    ReportService.getSurfaceTypePerCountry(countries, airports, runways)
    ReportService.getMostCommonRunwayIdentification(10, runways)
    CountriesAirportsRunwaysDetails(getAirportsAndRunwaysForCountries(matchedCountries, airports, runways))
  }

  def getAirportsAndRunwaysByCountryCode(code: String, countries: List[Country], airports: List[Airport], runways: List[Runway]): CountriesAirportsRunwaysDetails = {
    val matchedCountries = getCountryByCode(code, countries).distinct
    CountriesAirportsRunwaysDetails(getAirportsAndRunwaysForCountries(matchedCountries, airports, runways))
  }

  private def getAirportsAndRunwaysForCountries(countries: List[Country], airports: List[Airport], runways: List[Runway]): List[CountryWithAirportAndRunways] = {
    countries.map(country => {
      val airPorts = getAirportsByCountryCode(country.countryCode, airports)
      val airPortWithRunways = getRunwaysForAirports(airPorts, runways)
      CountryWithAirportAndRunways(country, airPortWithRunways)
    })
  }

  private def getCountryByName(name: String, countries: List[Country]): List[Country] = {
    countries.filter(_.countryName.toLowerCase.contains(name.toLowerCase))
  }

  private def getCountryByCode(countryCode: String, countries: List[Country]): List[Country] = {
    countries.filter(_.countryCode.equalsIgnoreCase(countryCode))
  }

  private def getAirportsByCountryCode(countryCode: String, airports: List[Airport]): List[Airport] = {
    airports.filter(_.countryCode.equalsIgnoreCase(countryCode))
  }

  private def getRunwaysForAirports(airports: List[Airport], runways: List[Runway]): List[AirportWithRunways] = {
    airports.map( airport => {
      AirportWithRunways(airport, getRunwaysByAirportId(airport.id, runways))
    })
  }

  private def getRunwaysByAirportId(airPortId: Int, runways: List[Runway]): List[Runway] = {
    runways.filter(_.airport_ref == airPortId)
  }


}
