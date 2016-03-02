package com.sample

import akka.actor.{ActorSystem, Props}

/**
  * Created by sp71427 on 2/25/2016.
  */
object BootPingPong extends App{

  val system = ActorSystem("PingPong")
  val barRef = system.actorOf(Props[Bar], "Bar")
  val fooRef = system.actorOf(Foo.props(barRef), "Foo")
  fooRef ! SendPing("Hello")

}
