package transformers.csv

import java.util

import models.{Country, Runway}
import org.apache.commons.csv.CSVRecord
import org.slf4j.LoggerFactory

import scala.annotation.tailrec
import scala.util.{Failure, Success, Try}

object RunwayCsvTransformer {

  private val logger = LoggerFactory.getLogger(this.getClass)

  @tailrec
  def transform(iterator: util.Iterator[CSVRecord], result: List[Runway]): List[Runway] = {
    if(iterator.hasNext) {
      val cSVRecord = iterator.next()
      populateRunwayObject(cSVRecord) match {
        case Success(runway) => {
          transform(iterator, runway :: result)
        }
        case Failure(ex) => {
          logger.error(s"Error reading runway data for entry ${cSVRecord} - ${ex}")
          transform(iterator, result)
        }
      }
    } else result
  }

  private def populateRunwayObject(cSVRecord: CSVRecord): Try[Runway] = {
    Try {
      Runway(cSVRecord.get("id").toInt, cSVRecord.get("airport_ref").toInt, cSVRecord.get("airport_ident"), cSVRecord.get("surface"), cSVRecord.get("le_ident"))
    }
  }
}
