package services

import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{Matchers, FunSpec}

class ReadCsvDataTest extends FunSpec with Matchers with ScalaFutures {

  describe("ReadCsvDataTest") {

    it("should read and transform runways data with removed dirty data") {
      ReadCsvData.readRunwaysData("sampleRunways.csv").futureValue.length should be(3)
    }

    it("should read and transform airport data with removed dirty data") {
      ReadCsvData.readAirportsData("sampleAirports.csv").futureValue.length should be(6)
    }

    it("should read countries data with removed dirty data") {
      ReadCsvData.readCountriesData("sampleCountries.csv").futureValue.length should be(4)
    }

  }
}
