package controllers

import cache.CsvCache
import models._
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{Matchers, FunSpec}
import org.scalatest.mock.MockitoSugar
import org.mockito.Mockito._
import play.api.libs.json.{JsString, Json}
import play.api.mvc.Result
import play.api.test.Helpers._
import play.api.test.{Helpers, FakeRequest}
import transformers.json.QueryModelsJsonTransformer
import scala.concurrent.ExecutionContext.Implicits.global

import scala.concurrent.Future


class QueryControllerTest extends FunSpec with Matchers with MockitoSugar with ScalaFutures with QueryModelsJsonTransformer{

  describe("QueryControllerTest") {

    val mockCache = mock[CsvCache]
    val controller = new QueryController(mockCache)
    val country = Country(1, "countryCode", "countryName")
    val airport = Airport(12, "idEnt", "airportName", "countryCode")
    val runway = Runway(11, 12, "airport_ident", "surface", "leIdent")
    val countryAirportRunwayData = CountryAirportRunwayData(
      List(country),
      List(airport),
      List(runway)
    )

    it("should return response by country name in json format") {

      when(mockCache.getDataSet) thenReturn (Future(countryAirportRunwayData))

      val result: Future[Result] = controller.getResponseByCountryName("countryName")(FakeRequest())
      Helpers.status(result) shouldBe(OK)

      val expectedResultObject = CountriesAirportsRunwaysDetails(List(CountryWithAirportAndRunways(country, List(AirportWithRunways(airport, List(runway))))))

      Json.parse(Helpers.contentAsString(result)) shouldBe(Json.toJson(expectedResultObject))

    }

    it("shpuld return response by country code in json format") {

      when(mockCache.getDataSet) thenReturn (Future(countryAirportRunwayData))

      val result: Future[Result] = controller.getResponseByCountryCode("countryCode")(FakeRequest())
      Helpers.status(result) shouldBe(OK)

      val expectedResultObject = CountriesAirportsRunwaysDetails(List(CountryWithAirportAndRunways(country, List(AirportWithRunways(airport, List(runway))))))

      Json.parse(Helpers.contentAsString(result)) shouldBe(Json.toJson(expectedResultObject))

    }
  }

}
