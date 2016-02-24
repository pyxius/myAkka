package com.sharding

import akka.actor.Actor
import akka.actor.Props
import akka.actor.OneForOneStrategy
import akka.actor.SupervisorStrategy
import akka.actor.ActorInitializationException
import akka.actor.DeathPactException


/**
  * Created by sp71427 on 2/21/2016.
  */
class CounterSupervisor extends Actor {
  val counter = context.actorOf(Props[Counter], "theCounter")

  override val supervisorStrategy = OneForOneStrategy() {
    case _: IllegalArgumentException     ⇒ SupervisorStrategy.Resume
    case _: ActorInitializationException ⇒ SupervisorStrategy.Stop
    case _: DeathPactException           ⇒ SupervisorStrategy.Stop
    case _: Exception                    ⇒ SupervisorStrategy.Restart
  }

  def receive = {
    case msg ⇒ counter forward msg
  }
}