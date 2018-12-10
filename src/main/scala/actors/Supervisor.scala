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
    case Authenticate(credentials, complete) =>
      val props = Props(new AuthenticationWorker(credentials = credentials, complete))
      val worker = context.actorOf(props)
      worker ! Authenticate
    case Authorize(cookie, complete) =>
      val props = Props(new AuthorizationWorker(cookie, complete))
      val worker = context.actorOf(props)
      worker ! Authorize
  }
}

