package actors

import akka.http.scaladsl.model.StatusCode
import akka.http.scaladsl.model.headers.{HttpCookiePair, HttpCredentials}

object Messages {
  case class Authenticate(credentials: Option[HttpCredentials], completerFunction: StatusCode => Unit)
  case class Authorize(cookie: HttpCookiePair, completerFunction: StatusCode => Unit)
  case object CompleteFailed
}
