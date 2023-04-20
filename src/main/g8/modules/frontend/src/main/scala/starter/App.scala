package starter

import com.raquo.laminar.api.L._
import org.scalajs.dom
import org.scalajs.dom.document
import starter.config.FrontEndConfig

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

@js.native
@JSImport("stylesheets/index.css", JSImport.Default)
object IndexCss extends js.Object

object App {

  val indexCss: IndexCss.type = IndexCss

  def main(args: Array[String]): Unit = {
    val _ = documentEvents(_.onDomContentLoaded).foreach { _ =>
      val config = FrontEndConfig.config
      dom.console.log("publishable key from config", config.publishableKey)
      dom.console.log("api server base url", config.apiServer.baseUrl)

      val container = document.getElementById("app-container")
      dom.console.log("container", container)

      val _ =
        render(
          container,
          div(
            cls := "m-4 p-4 border-4 border-dashed border-gray-200 flex items-center",
            div(
              cls := "space-y-4",
              div(
                cls := "text-2xl text-gray-800 font-black",
                "Hello!"
              ),
              div(
                cls := "grid grid-cols-2 gap-1 text-xs text-gray-500",
                div("Publishable key:"),
                div(
                  code(config.publishableKey)
                ),
                div("API Server:"),
                div(
                  code(config.apiServer.baseUrl)
                ),
              )
            )
          )
        )
    }(unsafeWindowOwner)
  }
}
