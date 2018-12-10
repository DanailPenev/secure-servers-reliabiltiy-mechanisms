package actors

import akka.http.scaladsl.model.headers.HttpCredentials

object Messages {
  case class Handle(credentials: Option[HttpCredentials])
  case class Authenticate(credentials: Option[HttpCredentials])
  case object CompleteSuccessful
  case object CompleteFailed
}
