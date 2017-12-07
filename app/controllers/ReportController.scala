package controllers

import javax.inject.Inject

import cache.CsvCache
import config.Properties
import models.{CommonRunwayIdentification, SurfaceForCountries, CountriesWithAirportsCount, CountriesAirportsRunwaysDetails}
import org.slf4j.LoggerFactory
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}
import services.{QueryService, ReadCsvData, ReportService}
import transformers.json.ReportModelsJsonTransformer

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class ReportController @Inject() (csvDataCache: CsvCache) extends Controller with ReportModelsJsonTransformer {

  def getMaxAirportNumberWithCountry() = Action.async {
    val resultF: Future[CountriesWithAirportsCount] = csvDataCache.getDataSet.map(countryAirportRunwayData => {
      ReportService.getCountriesWithHighestAirports(10, countryAirportRunwayData.countries, countryAirportRunwayData.airports)
    })
    resultF.map(result => {
      Ok(Json.toJson(result))
    })
  }

  def getMinAirportNumberWithCountry() = Action.async {
    val resultF: Future[CountriesWithAirportsCount] = csvDataCache.getDataSet.map(countryAirportRunwayData => {
      ReportService.getCountriesWithLowestAirports(10, countryAirportRunwayData.countries, countryAirportRunwayData.airports)
    })
    resultF.map(result => {
      Ok(Json.toJson(result))
    })
  }

  def getRunwayTypesByAirport() = Action.async {
    val resultF: Future[SurfaceForCountries] = csvDataCache.getDataSet.map(countryAirportRunwayData => {
      ReportService.getSurfaceTypePerCountry(countryAirportRunwayData.countries, countryAirportRunwayData.airports, countryAirportRunwayData.runways)
    })
    resultF.map(result => {
      Ok(Json.toJson(result))
    })
  }

  def getMostCommonRunwayIdent() = Action.async {
    val resultF: Future[CommonRunwayIdentification] = csvDataCache.getDataSet.map(countryAirportRunwayData => {
      ReportService.getMostCommonRunwayIdentification(10, countryAirportRunwayData.runways)
    })
    resultF.map(result => {
      Ok(Json.toJson(result))
    })
  }


}
