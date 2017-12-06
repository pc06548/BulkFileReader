package controllers

import javax.inject.Inject

import cache.CsvCache
import models.CountriesAirportsRunwaysDetails
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}
import services.QueryService
import transformers.json.QueryModelsJsonTransformer

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class QueryController @Inject() (csvDataCache: CsvCache) extends Controller with QueryModelsJsonTransformer {

  def getResponseByCountryName(name: String = "") = Action.async {
    val resultF: Future[CountriesAirportsRunwaysDetails] = csvDataCache.getDataSet.map(countryAirportRunwayData => {
      QueryService.getAirportsAndRunwaysByCountryName(name, countryAirportRunwayData.countries, countryAirportRunwayData.airports, countryAirportRunwayData.runways)
    })
    resultF.map(result => {
      Ok(Json.toJson(result))
    })
  }

  def getResponseByCountryCode(code: String = "") = Action.async {
    val resultF: Future[CountriesAirportsRunwaysDetails] = csvDataCache.getDataSet.map(countryAirportRunwayData => {
      QueryService.getAirportsAndRunwaysByCountryCode(code, countryAirportRunwayData.countries, countryAirportRunwayData.airports, countryAirportRunwayData.runways)
    })
    resultF.map(result => {
      Ok(Json.toJson(result))
    })
  }
}
