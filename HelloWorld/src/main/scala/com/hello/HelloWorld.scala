package com.hello

import akka.actor.{Props, Actor}

/**
  * Created by sp71427 on 1/24/2016.
  */
class HelloWorld extends Actor {


  @throws[Exception](classOf[Exception])
  override def preStart(): Unit = {
    val greeter = context.actorOf(Props[Greeter], "greeter")
    greeter ! Greeter.Greet
  }

  override def receive = {
    case Greeter.Done => context.stop(self)
  }
}
