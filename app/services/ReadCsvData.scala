package services

import java.io.FileReader
import java.util

import models.{Airport, Country, Runway}
import org.apache.commons.csv.{CSVFormat, CSVRecord}
import transformers.csv._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object ReadCsvData {

  def readCountriesData(fileName: String): Future[List[Country]] = readFile(s"/$fileName", CountryCsvTransformer.transform)
  def readAirportsData(fileName: String): Future[List[Airport]] = readFile(s"/$fileName", AirportCsvTransformer.transform)
  def readRunwaysData(fileName: String): Future[List[Runway]] = readFile(s"/$fileName", RunwayCsvTransformer.transform)

  private def readFile[T](fileName: String, transform: (util.Iterator[CSVRecord], List[T]) => List[T]): Future[List[T]] = {
    Future {
      val in = new FileReader(getClass.getResource(fileName).getFile)
      val records: util.Iterator[CSVRecord] = CSVFormat.EXCEL.withHeader().parse(in).iterator()
      transform(records, List.empty[T])
    }
  }
}