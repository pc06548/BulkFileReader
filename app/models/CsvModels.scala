package models

case class Country(id: Int, countryCode: String, countryName: String)

case class Airport(id:Int, idEnt: String, name: String, countryCode: String)

case class Runway(id: Int,airport_ref: Int, airport_ident: String, surface: String, leIdent: String)

case class CountryAirportRunwayData(countries: List[Country], airports: List[Airport], runways: List[Runway])


