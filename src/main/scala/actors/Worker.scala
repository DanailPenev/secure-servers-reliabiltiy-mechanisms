package actors

import actors.Messages.{Authenticate, CompleteSuccessful}
import akka.actor.Actor
import akka.http.scaladsl.server.directives.Credentials
import server.TryCatchAuthenticator

class Worker extends Actor {
  override def receive: Receive = {
    case Authenticate(creds) =>
      authenticate(Credentials(creds))
  }

  def authenticate(credentials: Credentials): Unit = {
    TryCatchAuthenticator.authenticate(credentials)
    context.parent ! CompleteSuccessful
  }
}
