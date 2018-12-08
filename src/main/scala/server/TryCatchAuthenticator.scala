package server

import akka.http.scaladsl.model.{StatusCode, StatusCodes}

object TryCatchAuthenticator {
  def authenticate(username: String, password: String): Unit = {
    val result = Config.map.get(username)
    result match {
      case Some(passwordHash) =>
        if (passwordHash != password) {
          throw new Exception
        }
      case None => throw new Exception
    }
  }

  def tryAuthenticate(username: String, password: String): StatusCode = {
    try {
      authenticate(username, password)
      StatusCodes.OK
    } catch {
      case _: Exception => StatusCodes.Unauthorized
    }
  }
}
