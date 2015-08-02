name := "pairiator"

version := "0.0.1"

libraryDependencies ++= Seq(
  "org.scalaj" %% "scalaj-http" % "1.1.5",
  "com.typesafe.play" %% "play-json" % "2.4.2",
  "joda-time" % "joda-time" % "2.8.1",
  "com.twitter.finatra" %% "finatra-http" % "2.0.0.M2",
  "com.twitter.finatra" %% "finatra-jackson" % "2.0.0.M2"
)
