package com.sharding

import akka.actor.ReceiveTimeout
import akka.contrib.pattern.ShardRegion
import akka.persistence.PersistentActor
import scala.concurrent.duration._
import akka.actor.ActorLogging

case object Increment
case object Decrement
case class Get(counterId: Long)
case class EntryEnvelope(id: Long, payload: Any)

case object Stop
case class CounterChanged(delta: Int)

class Counter extends PersistentActor with ActorLogging {
  import ShardRegion.Passivate

  context.setReceiveTimeout(120.seconds)

  // self.path.parent.name is the type name (utf-8 URL-encoded)
  // self.path.name is the entry identifier (utf-8 URL-encoded)
  override def persistenceId: String = self.path.parent.name + "-" + self.path.name

  var count = 0

  def updateState(event: CounterChanged): Unit =
    count += event.delta

  override def receiveRecover: Receive = {
    case evt: CounterChanged ⇒ updateState(evt)
    case msg => println(s"msg = $msg")
  }

  override def receiveCommand: Receive = {
    case Increment ⇒
      println("Got Increment Message in Counter...")
      persist(CounterChanged(+1))(updateState)
    case Decrement ⇒ persist(CounterChanged(-1))(updateState)
    case Get(_) ⇒
      log.info("Got Get Message in Counter...")
      sender() ! count
    case ReceiveTimeout ⇒ context.parent ! Passivate(stopMessage = Stop)
    case Stop ⇒ context.stop(self)
  }

  //#counter-extractor
  val idExtractor: ShardRegion.IdExtractor = {
    case EntryEnvelope(id, payload) ⇒ (id.toString, payload)
    case msg @ Get(id) ⇒ (id.toString, msg)
  }

  val shardResolver: ShardRegion.ShardResolver = msg ⇒ msg match {
    case EntryEnvelope(id, _) ⇒ (id % 12).toString
    case Get(id) ⇒ (id % 12).toString
  }
  //#counter-extractor

}