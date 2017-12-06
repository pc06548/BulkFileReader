package transformers.csv

import java.util

import models.Airport
import org.apache.commons.csv.CSVRecord

import scala.annotation.tailrec


object AirportCsvTransformer {
  @tailrec
  def transform(iterator: util.Iterator[CSVRecord], result: List[Airport]): List[Airport] = {
    if(iterator.hasNext) {
      val cSVRecord = iterator.next()
      val airPort = Airport(cSVRecord.get("id").toInt, cSVRecord.get("ident"), cSVRecord.get("name"), cSVRecord.get("iso_country"))
      transform(iterator, airPort :: result)
    } else result
  }
}