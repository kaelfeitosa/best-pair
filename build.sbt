import NativePackagerKeys._

name := "pairiator"

version := "0.0.1"

lazy val pairiator = project.in(file("."))
  .enablePlugins(DockerPlugin)

scalacOptions in Test ++= Seq(
  "-deprecation",
  "-encoding", "UTF-8",
  "-feature",
  "-language:existentials",
  "-language:higherKinds",
  "-language:implicitConversions",
  "-unchecked",
  "-Ywarn-dead-code",
  "-Ywarn-numeric-widen",
  "-Ywarn-unused-import"
)

libraryDependencies ++= Seq(
  "org.scalaj" %% "scalaj-http" % "1.1.5",
  "com.typesafe.play" %% "play-json" % "2.4.2",
  "joda-time" % "joda-time" % "2.8.1",
  "com.twitter.finatra" %% "finatra-http" % "2.0.0.M2",
  "com.twitter.finatra" %% "finatra-jackson" % "2.0.0.M2"
)

resolvers ++= Seq(
  Resolver.sonatypeRepo("snapshots"),
  "sonatypeReposcalaz-bintray" at "https://dl.bintray.com/scalaz/releases",
  "twitter-twitterrepo" at
  "https://oss.sonatype.org/content/repositories/snapshots"
)
