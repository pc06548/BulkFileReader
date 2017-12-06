package cache

import javax.inject.Inject

import config.Properties
import models.CountryAirportRunwayData
import play.api.cache.CacheApi
import services.ReadCsvData

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class CsvCache @Inject()(cache: CacheApi, properties: Properties) {

  def getDataSet: Future[CountryAirportRunwayData] = {
    val cachedCountryAirportRunwayData = cache.get[CountryAirportRunwayData]("CountryAirportRunwayData")

    cachedCountryAirportRunwayData match {
      case Some(countryAirportRunway) => Future.successful(countryAirportRunway)
      case None => cacheResult(readCsvData())
    }
  }

  private def readCsvData(): Future[CountryAirportRunwayData] = {
    val airportDataF = ReadCsvData.readAirportsData(properties.AIRPORT_CSV_FILE_NAME)
    val countryDataF = ReadCsvData.readCountriesData(properties.COUNTRIES_CSV_FILE_NAME)
    val runwayDataF = ReadCsvData.readRunwaysData(properties.RUNWAYS_CSV_FILE_NAME)
    for {
      airportData <- airportDataF
      countryData <- countryDataF
      runwayData <- runwayDataF
    } yield {
      CountryAirportRunwayData(countryData, airportData, runwayData)
    }
  }


  private def cacheResult(countryAirportRunwayDataF: Future[CountryAirportRunwayData]): Future[CountryAirportRunwayData] = {
    countryAirportRunwayDataF.map(countryAirportRunwayData => {
        cache.set("CountryAirportRunwayData", countryAirportRunwayData)
      countryAirportRunwayData
    })
  }

}
