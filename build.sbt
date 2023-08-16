import sbt._

lazy val base = (project in file("."))
  .aggregate(
    common,
    exercises
  )
  .settings(CommonSettings.commonSettings: _*)

lazy val common = project.settings(CommonSettings.commonSettings: _*)

lazy val exercises = project
  .settings(CommonSettings.commonSettings: _*)
  .dependsOn(common % "test->test;compile->compile")
  .enablePlugins(Cinnamon)

(Compile / runMain) := ((Compile / runMain) in exercises).evaluated
onLoad in Global := (onLoad in Global).value andThen (Command.process("project exercises", _))

resolvers in ThisBuild += "lightbend-commercial-mvn" at "https://repo.lightbend.com/pass/QAupqagb9QASiyATo4HKhj1CXJFz-vL934vcUlSDK2f2WtdD/commercial-releases"
resolvers in ThisBuild += Resolver.url("lightbend-commercial-ivy", url("https://repo.lightbend.com/pass/QAupqagb9QASiyATo4HKhj1CXJFz-vL934vcUlSDK2f2WtdD/commercial-releases"))(Resolver.ivyStylePatterns)
