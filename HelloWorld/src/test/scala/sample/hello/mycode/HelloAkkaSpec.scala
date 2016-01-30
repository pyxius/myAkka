package sample.hello.mycode
import akka.actor.{ActorSystem, Props}
import akka.testkit.{TestActorRef, ImplicitSender, TestKit}
import org.scalatest.{Matchers, BeforeAndAfterAll, FlatSpecLike}
import scala.concurrent.duration._



class HelloAkkaSpec(_system: ActorSystem)
  extends TestKit(_system)
      with ImplicitSender
      with Matchers
      with FlatSpecLike
      with BeforeAndAfterAll {

    def this() = this(ActorSystem("HelloAkkaSpec"))

    override def afterAll: Unit = {
      system.shutdown()
      system.awaitTermination(10.seconds)

    }

    "An HelloAkkaActor" should "be able to set a new greeting" in {
      val greeter = TestActorRef(Props[Greeter])
      greeter ! WhoToGreet("testkit")
      greeter.underlyingActor.asInstanceOf[Greeter].greeting should be("hello, testkit")
    }

    it should "be able to get a new greeting" in {
      val greeter = system.actorOf(Props[Greeter], "greeter")
      greeter ! WhoToGreet("testkit")
      greeter ! Greet
      expectMsgType[Greeting].message.toString should be("hello, testkit")
    }
  }
