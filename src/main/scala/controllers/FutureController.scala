package controllers

import akka.http.scaladsl.model.headers.{HttpCookiePair, HttpCredentials}
import akka.http.scaladsl.model.{StatusCode, StatusCodes}
import akka.http.scaladsl.server.directives.Credentials
import server.Config

import scala.concurrent.Future
import scala.util.{Failure, Success}
import scala.concurrent.ExecutionContext.Implicits.global


object FutureController {
  def authenticator(credentials: Option[HttpCredentials], complete: StatusCode => Unit): Unit = Future {
    Credentials(credentials) match {
      case p@Credentials.Provided(id) =>
        Config.users.get(id) match {
          case Some(secret) if !p.verify(secret) => StatusCodes.Unauthorized
          case None => StatusCodes.Unauthorized
          case _ => StatusCodes.OK
        }
      case _ => StatusCodes.Unauthorized
    }
  }.onComplete {
    case Success(result) => complete(result)
    case Failure(_) => complete(StatusCodes.Unauthorized)
  }

  def authorizeAdmin(cookie: HttpCookiePair, complete: StatusCode => Unit): Unit = Future {
    Config.cookies.get(cookie.value) match {
      case Some(username) if !Config.admins.contains(username) => StatusCodes.Forbidden
      case None => StatusCodes.Forbidden
      case _ => StatusCodes.OK
    }
  }.onComplete {
    case Success(result) => complete(result)
    case Failure(_) => complete(StatusCodes.Forbidden)
  }
}
