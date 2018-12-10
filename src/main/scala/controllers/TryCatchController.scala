package controllers

import akka.http.scaladsl.model.headers.{HttpCookiePair, HttpCredentials}
import akka.http.scaladsl.model.{StatusCode, StatusCodes}
import akka.http.scaladsl.server.directives.Credentials
import util.Common

object TryCatchController {
  def authenticate(credentials: Option[HttpCredentials], complete: StatusCode => Unit): Unit = {
    try {
      Common.authenticate(Credentials(credentials))
      complete(StatusCodes.OK)
    } catch {
      case _: Exception => complete(StatusCodes.Unauthorized)
    }
  }

  def authorizeAdmin(nameCookie: HttpCookiePair, complete: StatusCode => Unit): Unit = {
    try {
      Common.checkAdmin(nameCookie.value)
      complete(StatusCodes.OK)
    } catch {
      case _: Exception => complete(StatusCodes.Forbidden)
    }
  }
}
