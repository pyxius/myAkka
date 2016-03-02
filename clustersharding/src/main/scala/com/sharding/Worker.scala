package com.sharding

import akka.actor.ActorLogging
import akka.persistence.{SnapshotOffer, PersistentActor}

case class Cmd(data: String)

case class Evt(data: String)



case class WorkerState(events: List[String] = Nil) {
  def updated(evt: Evt): WorkerState = copy(evt.data :: events)

  def size: Int = events.length

  override def toString: String = events.reverse.toString
}



class Worker extends PersistentActor with ActorLogging {
  override def persistenceId = s"streamSubscriber-${self.path.name}"

  var state = WorkerState()

  def updateState(event: Evt): Unit =
    state = state.updated(event)

  def numEvents =
    state.size

  val receiveRecover: Receive = {
    case evt: Evt => updateState(evt)
    case SnapshotOffer(_, snapshot: WorkerState) => state = snapshot
  }

  val receiveCommand: Receive = {
    case Cmd(data) =>
      log.info(s"Worker received work $data")
      persist(Evt(s"${data}-${numEvents}"))(updateState)
      persist(Evt(s"${data}-${numEvents + 1}")) { event =>
        updateState(event)
        context.system.eventStream.publish(event)
      }
    case "snap" => saveSnapshot(state)
    case "print" => println(state)
    case _ => log.info(s"Unhandled message in Worker...")
  }



}


