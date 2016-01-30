import sbt.Keys._
import sbt._

object HelloWorldBuild extends Build {

	import Dependencies._
	resolvers ++= Seq(
	  "Maven Repository" at "https://repo1.maven.org/maven2/"
	)

	lazy val helloWorld = Project("helloWorld", file("helloWorld"))
		.settings(libraryDependencies ++= compile(akkaActor, akkaTestkit, scalaTest)
	)

}
