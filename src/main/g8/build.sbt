import org.scalajs.linker.interface.ESVersion
import sbtcrossproject.CrossPlugin.autoImport.crossProject
import sbtcrossproject.CrossPlugin.autoImport.CrossType

inThisBuild(
  List(
    organization      := "$organization$",
    version           := "$version$",
    scalaVersion      := ScalaVersions.v213,
    scalafmtOnCompile := true
  )
)

name := "$name$"

val sharedSettings = Seq.concat(
  ScalaOptions.fixOptions,
)

lazy val shared =
  (crossProject(JSPlatform, JVMPlatform).crossType(CrossType.Pure) in file(
    "modules/shared"
  )).disablePlugins(RevolverPlugin)
    .settings(
      libraryDependencies ++= Seq.concat(
        Dependencies.circe.value,
      )
    )
    .settings(sharedSettings)

lazy val frontend =
  project
    .in(
      file("modules/frontend")
    )
    .enablePlugins(ScalaJSPlugin)
    .disablePlugins(RevolverPlugin)
    .settings(
      Compile / fastLinkJS / scalaJSLinkerConfig ~= { _.withModuleKind(ModuleKind.ESModule) },
      Compile / fullLinkJS / scalaJSLinkerConfig ~= { _.withModuleKind(ModuleKind.CommonJSModule) },
      scalaJSLinkerConfig ~= { _.withESFeatures(_.withESVersion(ESVersion.ES5_1)) },
      Compile / scalaJSLinkerConfig ~= { _.withSourceMap(false) },
      scalaJSUseMainModuleInitializer := true,
      libraryDependencies ++= Seq.concat(
        Dependencies.laminar.value
      )
    )
    .settings(sharedSettings)
    .dependsOn(shared.js)

lazy val backend =
  project
    .in(file("modules/backend"))
    .settings(
      libraryDependencies ++= Seq.concat(),
      reStart / mainClass := Some("starter.boot.Boot"),
    )
    .settings(sharedSettings)
    .dependsOn(shared.jvm)
