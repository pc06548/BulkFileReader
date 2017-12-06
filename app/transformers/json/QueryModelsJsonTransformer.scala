package transformers.json

import models._
import play.api.libs.json._

trait QueryModelsJsonTransformer extends CsvModelsJsonTransformer {

  implicit val airportWithRunwaysFormatter = Json.writes[AirportWithRunways]
  implicit val countryWithAirportAndRunwaysFormatter = Json.writes[CountryWithAirportAndRunways]
  implicit val countriesAirportsRunwaysDetailsFormatter = Json.writes[CountriesAirportsRunwaysDetails]


  implicit object CountriesAirportsRunwaysDetailsListFormat extends Writes[List[CountryWithAirportAndRunways]] {
    def writes(countriesWithAirportAndRunways: List[CountryWithAirportAndRunways]): JsValue = {
      val jsonSeq: List[(String, JsValue)] = countriesWithAirportAndRunways.map(countryWithAirportAndRunways =>
        countryWithAirportAndRunways.country.id.toString -> Json.toJson(countryWithAirportAndRunways))
      JsObject(jsonSeq)
    }
  }

  implicit object AirportWithRunwaysListFormat extends Writes[List[AirportWithRunways]] {
    def writes(airportsWithRunways: List[AirportWithRunways]): JsValue = {
      val jsonSeq: List[(String, JsValue)] = airportsWithRunways.map(airportWithRunways =>
        airportWithRunways.airport.id.toString -> Json.toJson(airportWithRunways))
      JsObject(jsonSeq)
    }
  }

  implicit object RunwayListFormat extends Writes[List[Runway]] {
    def writes(runways: List[Runway]): JsValue = {
      val jsonSeq: List[(String, JsValue)] = runways.map(runway =>
        runway.id.toString -> Json.toJson(runway))
      JsObject(jsonSeq)
    }
  }

}
