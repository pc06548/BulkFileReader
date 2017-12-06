package models

case class SurfacePerCountry(country: Country, surfaces: List[String])
case class SurfaceForCountries(surfaceForCountries: List[SurfacePerCountry])

case class CountryWithAirportsCount(country: Country, airportCount: Int)
case class CountriesWithAirportsCount(countriesWithAirportsCount: List[CountryWithAirportsCount])

case class CommonRunwayIdentification(runwayIdentifications: List[String])

