package actors

import actors.Messages.{Authenticate, CompleteFailed, Handle, Terminate}
import akka.actor.SupervisorStrategy.Resume
import akka.actor.{Actor, OneForOneStrategy, Props}
import akka.http.scaladsl.model.StatusCodes

class Supervisor extends Actor {
  override val supervisorStrategy: OneForOneStrategy =
    OneForOneStrategy() {
      case _: Exception =>
        sender ! Terminate
        Resume
    }

  override def receive: Receive = {
    case Handle(request) =>
      val worker = context.actorOf(Props[Worker])
      worker ! Authenticate(request)
    case CompleteFailed(request) =>
      request.complete(StatusCodes.Unauthorized)
  }
}
