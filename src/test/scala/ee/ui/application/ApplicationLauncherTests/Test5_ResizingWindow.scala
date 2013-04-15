package ee.ui.application.ApplicationLauncherTests

import scala.concurrent.duration._
import ee.ui.display.Window
import ee.ui.display.Scene
import ee.ui.display.shapes.Rectangle
import ee.ui.display.traits.Size
import ee.ui.display.primitives.Color
import com.sun.javafx.tk.Toolkit

object Test5_ResizingWindow extends ApplicationLauncherTestBase {

  "show a window that resizes every 500 millis" in {

    val launcher = new TestApplicationLauncher {
      def createApplication() = new TestApplication with Engine {
        override def start(window: Window) = {

          window.title = "Resizing Window"
          window.width = 200
          window.height = 100

          show(window)

          for (i <- 1 to 9)
            timer((i * 500).millis) {
              window.width.value += 40
              window.height.value += 20
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