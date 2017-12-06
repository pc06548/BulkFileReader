package transformers.json

import models._
import play.api.libs.json._

trait ReportModelsJsonTransformer extends CsvModelsJsonTransformer {

  implicit val SurfacePerCountryFormatter = Json.writes[SurfacePerCountry]
  implicit val SurfaceForCountriesFormatter = Json.writes[SurfaceForCountries]
  implicit val CountryWithAirportsCountFormatter = Json.writes[CountryWithAirportsCount]
  implicit val CountriesWithAirportsCountFormatter = Json.writes[CountriesWithAirportsCount]
  implicit val CommonRunwayIdentificationFormatter = Json.writes[CommonRunwayIdentification]

}
