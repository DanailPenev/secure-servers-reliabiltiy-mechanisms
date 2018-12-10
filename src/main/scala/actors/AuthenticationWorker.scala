package actors

import actors.Messages.{Authenticate, CompleteFailed}
import akka.actor.Actor
import akka.http.scaladsl.model.headers.HttpCredentials
import akka.http.scaladsl.model.{StatusCode, StatusCodes}
import akka.http.scaladsl.server.directives.Credentials
import util.Common

class AuthenticationWorker(credentials: Option[HttpCredentials], complete: StatusCode => Unit ) extends Actor {
  override def receive: Receive = {
    case Authenticate =>
      authenticate(Credentials(credentials))
    case CompleteFailed =>
      complete(StatusCodes.Unauthorized)
      context stop self
  }

  def authenticate(credentials: Credentials): Unit = {
    Common.authenticate(credentials)
    complete(StatusCodes.OK)
    context stop self
  }
}
