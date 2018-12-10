package util

import akka.http.scaladsl.server.directives.Credentials
import server.Config

object Common {
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

  def checkAdmin(userCookie: String): Unit = {
    Config.cookies.get(userCookie) match {
      case Some(username) if !Config.admins.contains(username) => throw new Exception
      case None => throw new Exception
      case _ =>
    }
  }
}
