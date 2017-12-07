package controllers

import cache.CsvCache
import models._
import org.mockito.Mockito._
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.mock.MockitoSugar
import org.scalatest.{FunSpec, Matchers}
import play.api.libs.json.Json
import play.api.mvc.Result
import play.api.test.Helpers._
import play.api.test.{Helpers, FakeRequest}
import transformers.json.ReportModelsJsonTransformer
import scala.concurrent.ExecutionContext.Implicits.global

import scala.concurrent.Future

class ReportControllerTest extends FunSpec with Matchers with MockitoSugar with ScalaFutures with ReportModelsJsonTransformer {

  describe("ReportControllerTest") {

    val mockCache = mock[CsvCache]
    val controller = new ReportController(mockCache)
    val country = Country(1, "countryCode", "countryName")
    val airport = Airport(12, "idEnt", "airportName", "countryCode")
    val runway = Runway(11, 12, "airport_ident", "surface", "leIdent")
    val countryAirportRunwayData = CountryAirportRunwayData(
      List(country),
      List(airport),
      List(runway)
    )

    it("should get countries with maximum airports count") {
      when(mockCache.getDataSet) thenReturn (Future(countryAirportRunwayData))

      val result: Future[Result] = controller.getMaxAirportNumberWithCountry()(FakeRequest())
      Helpers.status(result) shouldBe(OK)

      val expectedResultObject = CountriesWithAirportsCount(List(CountryWithAirportsCount(country, 1)))

      Json.parse(Helpers.contentAsString(result)) shouldBe(Json.toJson(expectedResultObject))
    }

    it("should get countries with minimum airports count") {
      when(mockCache.getDataSet) thenReturn (Future(countryAirportRunwayData))

      val result: Future[Result] = controller.getMinAirportNumberWithCountry()(FakeRequest())
      Helpers.status(result) shouldBe(OK)

      val expectedResultObject = CountriesWithAirportsCount(List(CountryWithAirportsCount(country, 1)))

      Json.parse(Helpers.contentAsString(result)) shouldBe(Json.toJson(expectedResultObject))
    }

    it("should get runway types by airport") {
      when(mockCache.getDataSet) thenReturn (Future(countryAirportRunwayData))

      val result: Future[Result] = controller.getRunwayTypesByAirport()(FakeRequest())
      Helpers.status(result) shouldBe(OK)

      val expectedResultObject = SurfaceForCountries(List(SurfacePerCountry(country, List("surface"))))

      Json.parse(Helpers.contentAsString(result)) shouldBe(Json.toJson(expectedResultObject))
    }

    it("should get most common runway ident") {
      when(mockCache.getDataSet) thenReturn (Future(countryAirportRunwayData))

      val result: Future[Result] = controller.getMostCommonRunwayIdent()(FakeRequest())
      Helpers.status(result) shouldBe(OK)

      val expectedResultObject = CommonRunwayIdentification(List("leIdent"))

      Json.parse(Helpers.contentAsString(result)) shouldBe(Json.toJson(expectedResultObject))
    }
  }


}
