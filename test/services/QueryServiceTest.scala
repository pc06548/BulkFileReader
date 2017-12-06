package services

import models._
import org.scalatest.{FunSpec, Matchers}
import utils.TestSetup

class QueryServiceTest extends FunSpec with Matchers{

  describe("QueryServiceTest") {

    it("should get AirportsAndRunwaysByCountryName") {
      new TestSetup {
        val expectedAirport1 = Airport(6537,"00ID","Delta Shores Airport", "US")
        val expectedAirport2 = Airport(6523, "00A", "Total Rf Heliport", "US")
        val expectedRunways = List(Runway(246648, 6537, "00ID", "TURF", "08"))
        val expectedResult = CountriesAirportsRunwaysDetails(List(CountryWithAirportAndRunways(Country(302755,"US","United States"),
          List(AirportWithRunways(expectedAirport1, expectedRunways),
            (AirportWithRunways(expectedAirport2, List.empty))))))
        QueryService.getAirportsAndRunwaysByCountryName("United States", countries, airports, runways) should be(expectedResult)
      }
    }

    it("should get AirportsAndRunwaysByCountryName with partial match on name") {
      new TestSetup {
        QueryService.getAirportsAndRunwaysByCountryName("uR", countries, airports, runways).countryDetails.size should be(2)
      }
    }

    it("should get AirportsAndRunwaysByCountryCode") {
      new TestSetup {
        val expectedAirport1 = Airport(6537,"00ID","Delta Shores Airport", "US")
        val expectedAirport2 = Airport(6523, "00A", "Total Rf Heliport", "US")
        val expectedRunways = List(Runway(246648, 6537, "00ID", "TURF", "08"))
        val expectedResult = CountriesAirportsRunwaysDetails(List(CountryWithAirportAndRunways(Country(302755,"US","United States"),
        List(AirportWithRunways(expectedAirport1, expectedRunways),
          (AirportWithRunways(expectedAirport2, List.empty))))))
        QueryService.getAirportsAndRunwaysByCountryCode("US", countries, airports, runways) should be(expectedResult)
      }
    }
  }
}


