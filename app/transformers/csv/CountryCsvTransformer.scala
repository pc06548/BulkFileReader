package transformers.csv

import java.util

import models.Country
import org.apache.commons.csv.CSVRecord

import scala.annotation.tailrec

object CountryCsvTransformer {

  @tailrec
  def transform(iterator: util.Iterator[CSVRecord], result: List[Country]): List[Country] = {
    if(iterator.hasNext) {
      val cSVRecord = iterator.next()
      val country = Country(cSVRecord.get("id").toInt, cSVRecord.get("code"), cSVRecord.get("name"))
      transform(iterator, country :: result)
    } else result
  }
}