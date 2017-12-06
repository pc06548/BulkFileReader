package transformers.csv

import java.util

import models.Runway
import org.apache.commons.csv.CSVRecord

import scala.annotation.tailrec

object RunwayCsvTransformer {
  @tailrec
  def transform(iterator: util.Iterator[CSVRecord], result: List[Runway]): List[Runway] = {
    if(iterator.hasNext) {
      val cSVRecord = iterator.next()
      val runWay = Runway(cSVRecord.get("id").toInt, cSVRecord.get("airport_ref").toInt, cSVRecord.get("airport_ident"), cSVRecord.get("surface"), cSVRecord.get("le_ident"))
      transform(iterator, runWay :: result)
    } else result
  }
}
