import sbt._

object Versions {
  val AkkaVersion = "2.3.14"
  val AkkaTestkit = "2.3.14"
  val ScalaTestVer = "3.0.0-M1"
}

object Dependencies {
  import Versions._

  // scopes
  def compile (deps: ModuleID*): Seq[ModuleID] =  deps map (_ % "compile")

  lazy val akkaActor =  "com.typesafe.akka" %% "akka-actor" % AkkaVersion
  lazy val akkaTestkit = "com.typesafe.akka" %% "akka-testkit" % AkkaTestkit
  lazy val scalaTest = "org.scalatest" %% "scalatest" % ScalaTestVer
}
