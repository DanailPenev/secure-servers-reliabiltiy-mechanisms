package actors

import actors.Messages._
import akka.actor.SupervisorStrategy.Restart
import akka.actor.{Actor, OneForOneStrategy, Props}

class Supervisor() extends Actor {
  override val supervisorStrategy: OneForOneStrategy =
    OneForOneStrategy() {
      case _: Exception =>
        sender() ! CompleteFailed
        Restart
    }

  override def receive: Receive = {
    case Authenticate(creds, completer) =>
      val props = Props(new AuthenticationWorker(credentials = creds, completer))
      val worker = context.actorOf(props)
      worker ! Authenticate
    case Authorize(cookie, completer) =>
      val props = Props(new AuthorizationWorker(cookie, completer))
      val worker = context.actorOf(props)
      worker ! Authorize
  }
}

