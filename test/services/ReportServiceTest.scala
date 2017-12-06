package services

import org.scalatest.{Matchers, FunSpec}
import utils.TestSetup

class ReportServiceTest extends FunSpec with Matchers {

  describe("ReportServiceTest") {

    it("should get countries with highest airports") {
      new TestSetup {
        val result = ReportService.getCountriesWithHighestAirports(2, countries, airports)

        result.countriesWithAirportsCount.size should be(2)
        result.countriesWithAirportsCount.head.country.countryCode should be("AY")
        result.countriesWithAirportsCount.head.airportCount should be(3)
        result.countriesWithAirportsCount.last.country.countryCode should be("US")
        result.countriesWithAirportsCount.last.airportCount should be(2)
      }
    }

    it("should get countries with lowest airports") {
      new TestSetup {
        val result = ReportService.getCountriesWithLowestAirports(2, countries, airports)

        result.countriesWithAirportsCount.size should be(2)
        result.countriesWithAirportsCount.head.country.countryCode should be("UY")
        result.countriesWithAirportsCount.head.airportCount should be(0)
        result.countriesWithAirportsCount.last.country.countryCode should be("UR")
        result.countriesWithAirportsCount.last.airportCount should be(1)
      }
    }

    it("should get surface types by countries") {
      new TestSetup {
        val result = ReportService.getSurfaceTypePerCountry(countries, airports, runways)
        result.surfaceForCountries.size should be(3)

        val firstCountryToSurface = result.surfaceForCountries.head
        val secondCountryToSurface = result.surfaceForCountries.tail.head
        val thirdCountryToSurface = result.surfaceForCountries.last

        firstCountryToSurface.country.countryCode should be("AY")
        secondCountryToSurface.country.countryCode should be("UR")
        thirdCountryToSurface.country.countryCode should be("US")

        firstCountryToSurface.surfaces should be(List())
        secondCountryToSurface.surfaces should be(List())
        thirdCountryToSurface.surfaces should be(List("TURF"))
      }
    }

    it("should get most common runway identification") {
      new TestSetup {
        val result = ReportService.getMostCommonRunwayIdentification(2, runways)
        result.runwayIdentifications should be(List("08", "06"))
      }
    }

  }
}
