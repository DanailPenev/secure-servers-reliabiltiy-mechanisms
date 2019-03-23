package controllers

import akka.http.scaladsl.model.headers.{HttpCookiePair, HttpCredentials}
import akka.http.scaladsl.model.{StatusCode, StatusCodes}
import akka.http.scaladsl.server.directives.Credentials
import util.Common

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success}


object FutureController {
  def authenticate(credentials: Option[HttpCredentials], complete: StatusCode => Unit): Unit = Future {
    Common.authenticate(Credentials(credentials))
  }.onComplete {
    case Success(_) => complete(StatusCodes.OK)
    case Failure(_) => complete(StatusCodes.Unauthorized)
  }

  def authorizeAdmin(cookie: HttpCookiePair, complete: StatusCode => Unit): Unit = Future {
    Common.checkAdmin(cookie.value)
  }.onComplete {
    case Success(_) => complete(StatusCodes.OK)
    case Failure(_) => complete(StatusCodes.Forbidden)
  }
}
