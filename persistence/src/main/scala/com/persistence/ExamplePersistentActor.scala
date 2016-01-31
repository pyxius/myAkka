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

  def updateState(event: Evt): Unit =
    state = state.updated(event)

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
      log.info(s"Shutting Down Actor System...")
      persist(Evt(s"${data}-${numEvents}"))(updateState)


      log.info(s"Inside Receive Command....")
//      persist(Evt(s"${data}-${numEvents + 1}")) { event =>
//        updateState(event)
//        context.system.eventStream.publish(event)
//      }
      context.system.shutdown()

    case "snap"  => saveSnapshot(state)
    case "print" => println(state)
  }


  def recoveryExtender: Receive = {
    case WillNotProcessCmd(data) =>
      log.info(s"Inside recovery extender waiting for 10 seconds...")
      //log.info(s" Actor context is ${context}")
      self ! Cmd("Cmd1")
      self ! Cmd("Cmd2")
      Thread.sleep(10000)
      switchToDefaultContext
  }

  def switchToDefaultContext = {
    log.info(s" Actor context is ${context}")
    context become receiveCommand
    self ! Cmd("Hello")
  }
}




