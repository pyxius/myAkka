import sbt._

object Versions {
  val AkkaVersion = "2.3.14"
  val AkkaPersistenceVersion = "2.3.14"
  val AkkaTestkitVersion = "2.3.14"
  val ScalaTestVersion = "3.0.0-M1"
  val AkkaClusterVersion = "2.3.14"
  val AkkContribVersion = "2.3.14"
}

object Dependencies {
  import Versions._

  // scopes
  def compile (deps: ModuleID*): Seq[ModuleID] =  deps map (_ % "compile")

  lazy val akkaActor =  "com.typesafe.akka" %% "akka-actor" % AkkaVersion
  lazy val akkaPersistence = "com.typesafe.akka" %% "akka-persistence-experimental" % AkkaVersion
  lazy val akkaTestkit = "com.typesafe.akka" %% "akka-testkit" % AkkaTestkitVersion
  lazy val scalaTest = "org.scalatest" %% "scalatest" % ScalaTestVersion
  lazy val akkaCluster = "com.typesafe.akka" %% "akka-cluster" % AkkaClusterVersion
  lazy val akkaContrib = "com.typesafe.akka" %% "akka-contrib" % AkkContribVersion
  lazy val akkaRemote = "com.typesafe.akka" %% "akka-remote" % AkkaVersion
  lazy val akkaMultiNodeTestKit = "com.typesafe.akka" %% "akka-multi-node-testkit" % AkkaVersion
}
