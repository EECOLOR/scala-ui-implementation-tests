package ee.ui.application.ApplicationLauncherTests

import scala.concurrent.duration._
import ee.ui.display.Window
import ee.ui.display.Scene
import ee.ui.display.shapes.Rectangle
import ee.ui.display.traits.Size
import ee.ui.display.primitives.Color
import com.sun.javafx.tk.Toolkit
import ee.ui.display.traits.Position
import scala.concurrent._
import scala.concurrent.ExecutionContext.Implicits.global
import ee.ui.primitives.transformation.Shear
import ee.ui.primitives.transformation.Shear
import ee.ui.display.shapes.Text
import ee.ui.display.traits.Fill

object Test7_TextField extends ApplicationLauncherTestBase {

  "textfield that you can type in" in {

    val launcher = new TestApplicationLauncher {
      def createApplication() = new TestApplication with Engine {
        override def start(window: Window) = {

          window.title = "TextField"
          window.width = 1024
          window.height = 786

          val text = new Text with Size with Fill {
            width = 10
            height = 10
            text = "test text"
            fill = Color.BLACK
          }

          window.scene = new Scene {
            root = text
          }

          show(window)

          timer(2.seconds) {
            //text.width = 200
            text.text = "another test text"
          }

          timer(5.seconds) {
            hide(window)
          }
        }
      }
    }

    launch(launcher)

    ok
  }
}