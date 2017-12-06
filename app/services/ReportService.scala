package services

import models._

object ReportService {

  def getCountriesWithHighestAirports(count: Int, countries: List[Country], airports: List[Airport]): CountriesWithAirportsCount = {
    def sortFunction(l1: (String, List[Airport]), l2:(String, List[Airport])) = {
      l1._2.length > l2._2.length
    }
    val topSortedEntries = getSortedCountryCodeByAirportCount(countries, airports, sortFunction).take(count)
    val countryCodeByAirportCount: List[(String, Int)] = topSortedEntries.map(entry => (entry._1, entry._2.length))

    val countriesWithHighestAirports: List[CountryWithAirportsCount] = getCountryCodeToCountryMapping(countryCodeByAirportCount, countries)
    CountriesWithAirportsCount(countriesWithHighestAirports)
  }

  def getCountriesWithLowestAirports(count: Int, countries: List[Country], airports: List[Airport]): CountriesWithAirportsCount = {
    def sortFunction(l1: (String, List[Airport]), l2:(String, List[Airport])) = {
      l1._2.length < l2._2.length
    }

    def getCountriesWithoutAirport(countriesWithAirportsCount: List[(String, Int)]) = {
      val allCountries = countries.map(country => country.countryCode)
      val countryCodeWithAirport = countriesWithAirportsCount.map(_._1)
      allCountries.filter(!countryCodeWithAirport.contains(_))
    }

    val sortedCountryCodesByAirportCount = getSortedCountryCodeByAirportCount(countries, airports, sortFunction).map(s => (s._1, s._2.length))

    val countriesWithoutAirport = getCountriesWithoutAirport(sortedCountryCodesByAirportCount)
    val countryCodeWithLowestAirports: List[(String, Int)] = (countriesWithoutAirport.map((_,0)) ::: sortedCountryCodesByAirportCount.take(count - countriesWithoutAirport.length))
    val countriesWithLowestAirports: List[CountryWithAirportsCount] = getCountryCodeToCountryMapping(countryCodeWithLowestAirports, countries)
    CountriesWithAirportsCount(countriesWithLowestAirports)
  }

  def getSurfaceTypePerCountry(countries: List[Country], airports: List[Airport], runways: List[Runway]): SurfaceForCountries = {
    val airportToSurfaces: Map[Int, List[String]] = runways.groupBy(_.airport_ref).map(runway =>(runway._1, runway._2.map(_.surface)))
    val countryToAirports: List[(String, List[Int])] = airports.groupBy(_.countryCode).toList.map((countryCodeWithAirport) => (countryCodeWithAirport._1, countryCodeWithAirport._2.map(_.id)))

    val countryCodesToSurfacesMapping: List[(String, List[String])] = countryToAirports.map(country2Airport => {
      (country2Airport._1, country2Airport._2.foldLeft(List.empty[String])((acc, entry) => {
        airportToSurfaces.get(entry) match {
          case Some(list) => (acc ::: list).distinct
          case None => acc
        }
      }))
    })
    val countriesToSurfaceMapping = countryCodesToSurfacesMapping.map(entry => {
      SurfacePerCountry(countries.find(_.countryCode == entry._1).get, entry._2)
    })
    SurfaceForCountries(countriesToSurfaceMapping)
  }

  def getMostCommonRunwayIdentification(count: Int, runways: List[Runway]): CommonRunwayIdentification = {
    CommonRunwayIdentification(runways.groupBy(_.leIdent).toList.sortWith(_._2.length > _._2.length).take(count).map(_._1))
  }

  private def getSortedCountryCodeByAirportCount(countries: List[Country], airports: List[Airport], sortFunction: ((String, List[Airport]), (String, List[Airport])) => Boolean) = {
    val airportsByCountryCode: Map[String, List[Airport]] = airports.groupBy(_.countryCode)
    val sortedCountryCodesByAirportsCount: List[(String, List[Airport])] = airportsByCountryCode.toList.sortWith(sortFunction)
    sortedCountryCodesByAirportsCount
  }

  private def getCountryCodeToCountryMapping(countryCodeByAirportCount: List[(String, Int)], countries: List[Country]) = {
    countryCodeByAirportCount.map(entry => {
      CountryWithAirportsCount(countries.find(_.countryCode == entry._1).get, entry._2)
    })
  }
}
