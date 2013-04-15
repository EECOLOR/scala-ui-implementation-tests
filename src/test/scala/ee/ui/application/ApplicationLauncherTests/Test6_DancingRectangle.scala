package ee.ui.application.ApplicationLauncherTests

import scala.concurrent.duration._
import ee.ui.display.Window
import ee.ui.display.Scene
import ee.ui.display.shapes.Rectangle
import ee.ui.display.traits.Size
import ee.ui.display.primitives.Color
import com.sun.javafx.tk.Toolkit

object Test6_DancingRectangle extends ApplicationLauncherTestBase {

  "show a rectangle that dances across the screen" in {

    val launcher = new TestApplicationLauncher {
      def createApplication() = new TestApplication with Engine {
        override def start(window: Window) = {

          window.title = "Dancing Rectangle"
          window.width = 1024
          window.height = 786

          show(window)

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