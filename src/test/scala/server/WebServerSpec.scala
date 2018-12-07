package server

import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.scalatest.{Matchers, WordSpec}

class WebServerSpec extends WordSpec with Matchers with ScalatestRouteTest with Routes {

  "The service" should {
    "return a 'Hello!' response for GET requests to /hello" in {
      // tests:
      Get("/hello") ~> route ~> check {
        responseAs[String] shouldEqual "Hello!"
      }
    }

    "leave GET requests to other paths unhandled" in {
      // tests:
      Get("/kermit") ~> route ~> check {
        handled shouldBe false
      }
    }
  }
}