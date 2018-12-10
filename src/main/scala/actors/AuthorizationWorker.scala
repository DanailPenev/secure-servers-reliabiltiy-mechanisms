package actors

import actors.Messages.{Authorize, CompleteFailed}
import akka.actor.Actor
import akka.http.scaladsl.model.{StatusCode, StatusCodes}
import akka.http.scaladsl.model.headers.HttpCookiePair
import server.TryCatchAuthenticator

class AuthorizationWorker(cookie: HttpCookiePair, complete: StatusCode => Unit) extends Actor{
  override def receive: Receive = {
    case Authorize =>
      authorize(cookie.value)
    case CompleteFailed =>
      complete(StatusCodes.Forbidden)
      context stop self
  }

  def authorize(username: String): Unit = {
    TryCatchAuthenticator.checkAdmin(username)
    complete(StatusCodes.OK)
    context stop self
  }
}
