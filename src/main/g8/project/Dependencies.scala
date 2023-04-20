import org.portablescala.sbtplatformdeps.PlatformDepsPlugin.autoImport._
import sbt._

object Dependencies {

  val laminar: Def.Initialize[Seq[ModuleID]] = Def.setting {
    Seq(
      "com.raquo" %%% "laminar" % DependencyVersions.laminar
    )
  }

  val circe: Def.Initialize[Seq[ModuleID]] = Def.setting {
    Seq(
      "io.circe" %%% "circe-core"    % DependencyVersions.circe,
      "io.circe" %%% "circe-generic" % DependencyVersions.`circe`,
      "io.circe" %%% "circe-parser"  % DependencyVersions.circe
    )
  }

}
