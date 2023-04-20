package starter.config

import io.circe.generic.JsonCodec
import io.circe.parser._

import scala.scalajs.js
import scala.scalajs.js.JSON
import scala.scalajs.js.annotation.JSGlobal

@JsonCodec
final case class FrontEndConfig(
  apiServer: ApiServerConfig,
  publishableKey: String
)

@JsonCodec
final case class ApiServerConfig(
  baseUrl: String
)

object FrontEndConfig {

  @js.native
  @JSGlobal("window")
  private[this] object ConfigGlobalScope extends js.Object {

    val config: js.Object = js.native

  }

  lazy val config: FrontEndConfig = decode[FrontEndConfig](
    JSON.stringify(ConfigGlobalScope.config)
  ).fold(throw _, identity)

}
