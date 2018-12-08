package server

import akka.http.scaladsl.model.{StatusCode, StatusCodes}
import akka.http.scaladsl.server.directives.Credentials

object TryCatchAuthenticator {
  def authenticate(credentials: Credentials): Unit =
    credentials match {
      case p @ Credentials.Provided(id) =>
        val result = Config.map.get(id)
        result match {
          case Some(secret) if !p.verify(secret) => throw new Exception
          case None => throw new Exception
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
