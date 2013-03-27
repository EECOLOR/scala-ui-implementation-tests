package ee.ui.application

import ee.ui.implementation.EngineImplementationContract
import ee.ui.implementation.WindowImplementationHandler
import ee.ui.implementation.ExitHandler

abstract class ExampleApplicationLauncher extends ApplicationLauncher {
  
  def launch(createApplication: () => Application with Engine, args: Array[String]) =
    createApplication()

  type Engine = ExampleEngine

  trait ExampleEngine extends EngineImplementationContract {
    val windowImplementationHandler = new WindowImplementationHandler {
      def hide(window: ee.ui.display.Window): Unit = ???
      def show(window: ee.ui.display.Window): Unit = ???
    }
    val exitHandler = new ExitHandler {
      def exit(application:Application):Unit = application.stop()
    }
    val settings = new ApplicationSettings(application)
  }
}