package utils

import models._
import services.ReadCsvData

import scala.concurrent.Await
import scala.concurrent.duration.Duration


trait TestSetup {
  val airports: List[Airport] = Await.result(ReadCsvData.readAirportsData("sampleAirports.csv"), Duration.Inf)
  val countries: List[Country] = Await.result(ReadCsvData.readCountriesData("sampleCountries.csv"), Duration.Inf)
  val runways: List[Runway] = Await.result(ReadCsvData.readRunwaysData("sampleRunways.csv"), Duration.Inf)
}
