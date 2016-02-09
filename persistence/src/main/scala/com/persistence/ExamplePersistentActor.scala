package com.persistence

import akka.actor.ActorLogging
import akka.persistence._

case class Cmd(data: String)
case class Evt(data: String)
case class WillNotProcessCmd(data: String)

case class ExampleState(events: List[String] = Nil) {
  def updated(evt: Evt): ExampleState = copy(evt.data :: events)
  def size: Int = events.length
  override def toString: String = events.reverse.toString
}

class ExamplePersistentActor extends PersistentActor with ActorLogging{
  override def persistenceId = "sample-id-1"

  @throws[Exception](classOf[Exception])
  override def preStart(): Unit = {
    super.preStart()
    log.debug("Inside preStart of Actor...")
  }

  var state = ExampleState()

  def updateState(event: Evt): Unit = {
    log.info(s"Inside update state...$event")
    state = state.updated(event)
  }
  def numEvents =
    state.size

  val receiveRecover: Receive = {
    case RecoveryCompleted => {
      log.debug(s"Recovery completed for ExamplePersistentActor ")
      context become recoveryExtender
      self ! WillNotProcessCmd("Sleeper")
    }
    case evt: Evt                                 => updateState(evt)
    case SnapshotOffer(_, snapshot: ExampleState) => state = snapshot
  }

  val receiveCommand: Receive = {
    case Cmd(data) =>
      persist(Evt(s"${data}-${numEvents}"))(updateState)
      log.info(s"Inside Receive Command....")
//      persist(Evt(s"${data}-${numEvents + 1}")) { event =>
//        updateState(event)
//        context.system.eventStream.publish(event)
//      }
      log.info("Sleeping before shutdown...")
      Thread.sleep(10000)
      log.info(s"Shutting Down Actor System...")
      context.system.shutdown()

    case "snap"  => saveSnapshot(state)
    case "print" => println(state)
  }

  /**
    *
    * @return
    */
  def recoveryExtender: Receive = {
    case WillNotProcessCmd(data) =>
      log.info(s" Sending Recovery1,Recovery2 to Actor")
      self ! Cmd("Recovery1")
      self ! Cmd("print")

      log.info(s"Inside recovery extender waiting for 10 seconds...")
      Thread.sleep(10000)
      switchToDefaultContext
  }

  def switchToDefaultContext = {
    log.info(s" Actor context is ${context.self.path}")
    context become receiveCommand
    self ! Cmd("Command1")
    self ! Cmd("print")

  }
}




