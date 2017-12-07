package transformers.csv

import java.util

import models.Country
import org.apache.commons.csv.CSVRecord
import org.slf4j.LoggerFactory

import scala.annotation.tailrec
import scala.util.{Failure, Success, Try}

object CountryCsvTransformer {

  private val logger = LoggerFactory.getLogger(this.getClass)

  @tailrec
  def transform(iterator: util.Iterator[CSVRecord], result: List[Country]): List[Country] = {
    if(iterator.hasNext) {
      val cSVRecord = iterator.next()
      populateCountryObject(cSVRecord) match {
        case Success(country) => {
          transform(iterator, country :: result)
        }
        case Failure(ex) => {
          logger.error(s"Error reading country data for entry ${cSVRecord} - ${ex}")
          transform(iterator, result)
        }
      }
    } else result
  }

  private def populateCountryObject(cSVRecord: CSVRecord): Try[Country] = {
    Try {
      Country(cSVRecord.get("id").toInt, cSVRecord.get("code"), cSVRecord.get("name"))
    }
  }
}