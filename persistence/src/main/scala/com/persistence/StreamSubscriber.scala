package com.persistence

import akka.actor.ActorLogging
import akka.persistence.{SnapshotOffer, RecoveryCompleted, PersistentActor}

/**
  * StreamSubscriberState (this is persisted state of the Actor)
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
    println(s"Inside update state...$event")
    state = state.updated(event)
  }

  def numEvents = state.size


  /**
    * this is called when Actor recovers from a persisted state
    * @return
    */
  def receiveRecover: Receive = {
    case RecoveryCompleted =>
      println(s"Recovery completed for StreamSubscriber. Changing StreamSubscriber context to processRecoveryEvents")
      println("Simulating call delay to change context of SS to processRecoveryEvents"); Thread.sleep(4000)
      context become processRecoveryEvents
      self ! RecoveryExtender(5000)
    case evt: Evt => {
      println(s"SS - Got an Evt from persisted state ${evt}")
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
      println(s"SS - Got msg ${data} in receiveCommand...")
      persist(Evt(s"${data}-${numEvents}"))(updateState)
    case _ =>
      println(s"Unhandled msg type in Receive Command...")
  }

  /**
    *
    * @return
    */
  def processRecoveryEvents: Receive = {
    case RecoveryExtender(data) =>
      println(s"StreamSubscriber has recovered from persistence and handling post recovery (Replay scenario)...for ${data} millis")
      Thread.sleep(data)
      println(s"Full recovery done for StreamSubscriber(Replay completed) switching to receiveCommand mode...")
      unstashAll()
      context become receiveCommand
     // unstashAll()
//    case Cmd(data) =>
//      println(s"Inside processRecoveryEvents of StreamSubscriber. Got message ${data} will stash this message...")
//      stash()
    case x =>
      println(s"Unhandled msg in processRecoveryEvents...")
      stash()
    case _ =>
      println(s"Absolutely Unhandled msg in processRecoveryEvents...")
  }

}
