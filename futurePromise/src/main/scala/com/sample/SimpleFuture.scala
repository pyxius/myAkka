package com.sample

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Await, Future}
import scala.concurrent.duration._

/**
  * Created by sp71427 on 2/24/2016.
  */
object SimpleFuture extends App {

  val sum = count(5)
  sum onComplete {
    case num => println(s"Total is $num")
  }
  Await.result(sum, 20 seconds)
  println(s"something")
  Thread.sleep(2000)

  /**
    * recursively add numbers from number to 1 as Futures
    * @param number
    * @return
    */
  def count(number: Int): Future[Int] = Future {
    var total = 0
    if(number != 0) {
      total = number * Await.result(count(number -1), number.seconds)
    }else {
      total =1
    }
    total
  }
}
