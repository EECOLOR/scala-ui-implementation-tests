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

object Test6_DancingRectangle extends ApplicationLauncherTestBase {

  "show a rectangle that dances across the screen" in {

    val launcher = new TestApplicationLauncher {
      def createApplication() = new TestApplication with Engine {
        override def start(window: Window) = {

          window.title = "Dancing Rectangle"
          window.width = 1024
          window.height = 786

          val rectangle = new Rectangle with Position with Size {
            width = 200
            height = 100
            fill = Color(0xFF0000)
          }

          window.scene = new Scene {
            root = rectangle
          }

          show(window)

          val end = 9.seconds.fromNow

          rectangle.transformations += Shear()

          val animations = List[(Deadline, FiniteDuration, Double => Unit)](
            (0.seconds.fromNow, 1.second, { i => rectangle.transformations.update(0, Shear(i)) }),
            (1.second.fromNow, 1.second, { i => rectangle.transformations.update(0, Shear(1 - i)) }),
            (2.seconds.fromNow, 1.second, { i => rectangle.x = i * 400 }),
            (3.seconds.fromNow, 1.second, { i => rectangle.y = i * 300 }),
            (4.seconds.fromNow, 2.seconds, { i => rectangle.scaleX = 1 + i * 3 }),
            (6.seconds.fromNow, 2.seconds, { i => rectangle.scaleY = 1 + i * 2 }),
            (8.seconds.fromNow, 2.seconds, { i => rectangle.rotation = i * 360 }))

          future {
            while (end.hasTimeLeft) {
              animations foreach {
                case (start, duration, animation) if (start.isOverdue && !(start + duration).isOverdue) => {
                  val time = (-start.timeLeft) min duration
                  val progress = time / duration
                  animation(progress)
                }
                case _ =>
              }
              Thread sleep 40
            }
          }

          timer(11.seconds) {
            hide(window)
          }
        }
      }
    }

    launch(launcher)

    ok
  }
}