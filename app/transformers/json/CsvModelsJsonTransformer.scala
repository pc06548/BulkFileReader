package transformers.json

import models._
import play.api.libs.json._

trait CsvModelsJsonTransformer {
  implicit val airportFormatter = Json.writes[Airport]
  implicit val runwayFormatter = Json.writes[Runway]
  implicit val countryFormatter = Json.writes[Country]

}
