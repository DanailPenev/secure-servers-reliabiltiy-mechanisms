package server

import akka.actor.ActorSystem
import akka.http.scaladsl.server.directives.Credentials

object ActorAuthenticator {
  val system = ActorSystem("yolo-system")

  def authenticator(credentials: Credentials): Credentials = {
    credentials
  }

}
