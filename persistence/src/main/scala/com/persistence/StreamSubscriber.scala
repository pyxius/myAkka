package com.persistence

import akka.actor.ActorLogging
import akka.persistence.{PersistentActor, RecoveryCompleted, SnapshotOffer}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
/**
  * StreamSubscriberState (this is persisted state of the Actor)
  *
  * @param events
  */
case class StreamSubscriberState(events: List[String] = Nil) {
  def updated(evt: Evt): StreamSubscriberState = copy(evt.data :: events)
  def size: Int = events.length
  override def toString: String = events.reverse.toString
}

case class RecoveryExtender(delay:Long)

/**
  * Created by sp71427 on 1/31/2016.
  */
class StreamSubscriber extends PersistentActor with ActorLogging {

  override def persistenceId = "stream-subscriber-id-1"

  var state = StreamSubscriberState()

  def updateState(event: Evt): Unit = {
    log.info(s"Inside update state...$event")
    state = state.updated(event)
  }

  def numEvents = state.size


  /**
    * this is called when Actor recovers from a persisted state
    *
    * @return
    */
  def receiveRecover: Receive = {
    case RecoveryCompleted =>
      log.info(s"Recovery completed for StreamSubscriber. Changing StreamSubscriber context to processRecoveryEvents")
      log.info("Simulating call delay to change context of SS to processRecoveryEvents")
      val replay = Future {
        Thread.sleep(5000)
        "DelayInStateRecover"
      }
      replay onSuccess {
        case "DelayInStateRecover" => log.info(s"DelayInStateRecover completed")
      }
      log.info(s"Changing context of SS to processRecoveryEvents")
      context become processRecoveryEvents
      self ! RecoveryExtender(5000)
    case evt: Evt => {
      log.info(s"SS - Got an Evt from persisted state ${evt}")
      updateState(evt)
    }
    case SnapshotOffer(_, snapshot: StreamSubscriberState) => state = snapshot

  }

  /**
    *
    * @return
    */
  def receiveCommand: Receive = {
    case Cmd(data) =>
      log.info(s"SS - Got msg ${data} in receiveCommand...")
      persist(Evt(s"${data}-${numEvents}"))(updateState)
    case _ =>
      log.info(s"Unhandled msg type in Receive Command...")
  }

  /**
    *
    * @return
    */
  def processRecoveryEvents: Receive = {
    case RecoveryExtender(data) =>
      log.info(s"StreamSubscriber has recovered from persistence and handling post recovery (Replay scenario)...for ${data} millis")
      val replay = Future {
        Thread.sleep(data)
        "ReplayComplete"
      }
      replay onSuccess {
        case "ReplayComplete" => log.info("Replay Completed...")
      }
      log.info(s"Full recovery done for StreamSubscriber(Replay completed) switching to receiveCommand mode...")
      context become receiveCommand
      unstashAll()
    case x =>
      log.info(s"Unhandled msg in processRecoveryEvents...will stash it. ${x}")
      stash()
  }

}
