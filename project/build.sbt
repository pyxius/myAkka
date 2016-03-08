addSbtPlugin("com.github.mpeltonen" % "sbt-idea" % "1.6.0")

addSbtPlugin("com.typesafe.sbt" % "sbt-multi-jvm" % "0.3.8")

addSbtPlugin("com.typesafe.sbt" % "sbt-native-packager" % "1.0.0")

addSbtPlugin("org.xerial.sbt" % "sbt-pack" % "0.7.9")

addSbtPlugin("de.heikoseeberger" % "sbt-header" % "1.5.1")

addSbtPlugin("com.typesafe.sbt"  % "sbt-git"         % "0.8.5")

addSbtPlugin("me.lessis"         % "bintray-sbt"     % "0.3.0")

addSbtPlugin("org.scalariform"   % "sbt-scalariform" % "1.5.0")

resolvers += "Sonatype snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/"

resolvers += Resolver.sbtPluginRepo("public")
