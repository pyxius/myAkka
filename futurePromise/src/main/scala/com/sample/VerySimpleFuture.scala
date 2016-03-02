package com.sample

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent._
import scala.concurrent.Await
import scala.concurrent.duration._

/**
  * Created by sp71427 on 2/25/2016.
  */
object VerySimpleFuture extends App  {

  sayGreeting.onComplete {
    case msg => println(s" Saying $msg")
  }

  Await.result(sayGreeting, 1 seconds)

  /**
    *
    * @return
    */
  def sayGreeting = Future {
//    Thread.sleep(2000)
    s"Hello World"
  }


}
