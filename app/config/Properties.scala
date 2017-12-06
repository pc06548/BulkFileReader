package config

import javax.inject.Inject

class Properties @Inject() (configuration: play.api.Configuration) {

  def AIRPORT_CSV_FILE_NAME = getFromPropertyFile("airports_csv")
  def COUNTRIES_CSV_FILE_NAME = getFromPropertyFile("countries_csv")
  def RUNWAYS_CSV_FILE_NAME = getFromPropertyFile("runways_csv")

  private def getFromPropertyFile(propName: String): String = {
    configuration.getString(propName).get
  }
}
