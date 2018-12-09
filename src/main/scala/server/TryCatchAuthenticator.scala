package server

import akka.http.scaladsl.model.headers.HttpCookiePair
import akka.http.scaladsl.model.{StatusCode, StatusCodes}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.directives.Credentials

object TryCatchAuthenticator {
  def checkAdmin(userCookie: String): Unit = {
    Config.cookies.get(userCookie) match {
      case Some(username) if !Config.admins.contains(username) => throw new Exception
      case None => throw new Exception
      case _ =>
    }
  }

  def isAdmin(nameCookie: HttpCookiePair): Option[StatusCode] = {
    try {
      checkAdmin(nameCookie.value)
      Option(StatusCodes.OK)
    } catch {
      case _: Exception => Option(StatusCodes.Forbidden)
    }
  }

  def authenticate(credentials: Credentials): Unit =
    credentials match {
      case p @ Credentials.Provided(id) =>
        Config.users.get(id) match {
          case Some(secret) if !p.verify(secret) => throw new Exception
          case None => throw new Exception
          case _ =>
        }
      case _ => throw new Exception
    }

  def authenticator(credentials: Credentials): Option[StatusCode] = {
    try {
      authenticate(credentials)
      Option(StatusCodes.OK)
    } catch {
      case _: Exception => Option(StatusCodes.Unauthorized)
    }
  }
}
