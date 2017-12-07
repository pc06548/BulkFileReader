package transformers.csv

import java.util

import models.Airport
import org.apache.commons.csv.CSVRecord
import org.slf4j.LoggerFactory

import scala.annotation.tailrec
import scala.util.{Success, Try, Failure}


object AirportCsvTransformer {

  private val logger = LoggerFactory.getLogger(this.getClass)

  @tailrec
  def transform(iterator: util.Iterator[CSVRecord], result: List[Airport]): List[Airport] = {
    if(iterator.hasNext) {
      val cSVRecord: CSVRecord = iterator.next()
      populateAirportObject(cSVRecord) match {
        case Success(airport) => {
          transform(iterator, airport :: result)
        }
        case Failure(ex) => {
          logger.error(s"Error reading airport data for entry ${cSVRecord} - ${ex}")
          transform(iterator, result)
        }
      }
    } else result
  }

  private def populateAirportObject(cSVRecord: CSVRecord): Try[Airport] = {
    Try {
      Airport(cSVRecord.get("id").toInt, cSVRecord.get("ident"), cSVRecord.get("name"), cSVRecord.get("iso_country"))
    }
  }
}