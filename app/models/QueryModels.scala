package models

case class AirportWithRunways(airport: Airport, runways: List[Runway])
case class CountryWithAirportAndRunways(country: Country, airportsWithRunways: List[AirportWithRunways])
case class CountriesAirportsRunwaysDetails(countryDetails: List[CountryWithAirportAndRunways])