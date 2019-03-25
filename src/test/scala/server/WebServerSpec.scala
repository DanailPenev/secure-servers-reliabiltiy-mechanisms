package server

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.model.headers.{BasicHttpCredentials, Cookie}
import akka.http.scaladsl.server.MissingCookieRejection
import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.scalatest.{Matchers, WordSpec}

class WebServerSpec extends WordSpec with Matchers with ScalatestRouteTest with Routes {
  val models: Vector[String] = Vector(Config.ActorsPath, Config.ExceptionsPath, Config.FuturesPath)
  val validCredentials = BasicHttpCredentials("User", "ObfuscatedPassword")
  val invalidCredentials = BasicHttpCredentials("John", "p4ssw0rd")
  val validCookie = Cookie("userName" -> "RandomlyGeneratedCookie")
  val invalidCookie = Cookie("userName" -> "FakeCookie")

  "The service" should {
    "return a status OK response for GET requests to /status" in {
      // tests:
      Get("/status") ~> route ~> check {
        responseAs[String] shouldEqual """{"status": "ok"}"""
      }
    }

    "leave GET requests to other paths unhandled" in {
      // tests:
      Get("/fail") ~> route ~> check {
        handled shouldBe false
      }
    }
  }

  // Unit Tests
  for (model <- models) {
    s"The authentication service for $model" should {
      "authenticate properly with good basic auth params" in {
        Get(s"/$model/${Config.LoginPath}") ~> addCredentials(validCredentials) ~> route ~> check {
          status shouldEqual StatusCodes.OK
        }
      }

      "not authenticate when invalid credentials" in {
        Get(s"/$model/${Config.LoginPath}") ~> addCredentials(invalidCredentials) ~> route ~> check {
          status shouldEqual StatusCodes.Unauthorized
        }
      }

      "not authenticate when no auth provided" in {
        Get(s"/$model/${Config.LoginPath}") ~> route ~> check {
          status shouldEqual StatusCodes.Unauthorized
        }
      }
    }

    s"The authorization service for $model" should {
      "authorize properly with a good cookie" in {
        Get(s"/$model/${Config.AdminPath}") ~> validCookie ~> route ~> check {
          status shouldEqual StatusCodes.OK
        }
      }

      "reject if cookie is for non-admin user" in {
        Get(s"/$model/${Config.AdminPath}") ~> invalidCookie ~> route ~> check {
          status shouldEqual StatusCodes.Forbidden
        }
      }

      "reject if a cookie is missing" in {
        // missing cookie
        Get(s"/$model/${Config.AdminPath}") ~> route ~> check {
          rejection shouldEqual MissingCookieRejection("userName")
        }
      }
    }
  }

  // "Stress" tests
  "The Actor Service" should {
    "not fail when more than 10 requests are sent" in {
      for (_ <- 1 to 20) {
        Get(s"/${Config.ActorsPath}/${Config.LoginPath}") ~> addCredentials(validCredentials) ~> route ~> check {
          status shouldEqual StatusCodes.OK
        }
        Get(s"/${Config.ActorsPath}/${Config.AdminPath}") ~> validCookie ~> route ~> check {
          status shouldEqual StatusCodes.OK
        }
      }
    }
  }

}
