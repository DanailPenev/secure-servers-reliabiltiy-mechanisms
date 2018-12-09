package server

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.model.headers.{BasicHttpCredentials, Cookie}
import akka.http.scaladsl.server.{MissingCookieRejection, Route}
import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.scalatest.{Matchers, WordSpec}

class WebServerSpec extends WordSpec with Matchers with ScalatestRouteTest with Routes {

  "The service" should {
    "return a 'Hello!' response for GET requests to /hello" in {
      // tests:
      Get("/hello") ~> route ~> check {
        responseAs[String] shouldEqual """{"status": "ok"}"""
      }
    }

    "leave GET requests to other paths unhandled" in {
      // tests:
      Get("/kermit") ~> route ~> check {
        handled shouldBe false
      }
    }
  }

  "The authentication service" should {
    "authenticate properly with good basic auth params" in {
      val validCredentials = BasicHttpCredentials("User", "ObfuscatedPassword")
      Get("/login") ~> addCredentials(validCredentials) ~> route ~> check {
        status shouldEqual StatusCodes.OK
      }
    }

    "not authenticate when invalid credentials" in {
      val invalidCredentials = BasicHttpCredentials("John", "p4ssw0rd")
      Get("/login") ~> addCredentials(invalidCredentials) ~> route ~> check {
        status shouldEqual StatusCodes.Unauthorized
      }
    }

    "not authenticate when no auth provided" in {
      Get("/login") ~> route ~> check {
        status shouldEqual StatusCodes.Unauthorized
      }
    }
  }

  "The authorization service" should {
    "authorize properly with a good cookie" in {
      Get("/admin") ~> Cookie("userName" -> "RandomlyGeneratedCookie") ~> route ~> check {
        status shouldEqual StatusCodes.OK
      }
    }

    "reject if cookie is for non-admin user" in {
      Get("/admin") ~> Cookie("userName" -> "FakeCookie") ~> route ~> check {
        status shouldEqual StatusCodes.Forbidden
      }
    }

    "reject if a cookie is missing" in {
      // missing cookie
      Get("/admin") ~> route ~> check {
        rejection shouldEqual MissingCookieRejection("userName")
      }
    }
  }
}
