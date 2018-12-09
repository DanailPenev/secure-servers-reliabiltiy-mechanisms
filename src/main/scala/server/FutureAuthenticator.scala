package server

import akka.http.scaladsl.model.{StatusCode, StatusCodes}
import akka.http.scaladsl.server.directives.Credentials

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object FutureAuthenticator {
  def checkAdmin(userCookie: String): Future[Option[StatusCode]] = Future {
    Config.cookies.get(userCookie) match {
      case Some(username) if !Config.admins.contains(username) => Some(StatusCodes.Forbidden)
      case None => Some(StatusCodes.Forbidden)
      case _ => Some(StatusCodes.OK)
    }
  }

  def authenticator(credentials: Credentials): Future[Option[StatusCode]] = Future {
    credentials match {
      case p@Credentials.Provided(id) =>
        Config.users.get(id) match {
          case Some(secret) if !p.verify(secret) => Some(StatusCodes.Unauthorized)
          case None => Some(StatusCodes.Unauthorized)
          case _ => Some(StatusCodes.OK)
        }
      case _ => Some(StatusCodes.Unauthorized)
    }
  }
}

