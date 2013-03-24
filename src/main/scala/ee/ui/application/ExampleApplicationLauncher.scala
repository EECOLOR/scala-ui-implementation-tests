package ee.ui.application

import ee.ui.display.implementation.EngineImplementationContract
import ee.ui.display.implementation.WindowImplementationHandler

abstract class ExampleApplicationLauncher extends ApplicationLauncher {
  
  def launch(createApplication: () => Application with Engine, args: Array[String]) =
    createApplication()

  type Engine = ExampleEngine

  trait ExampleEngine extends EngineImplementationContract {
    val windowImplementationHandler = new WindowImplementationHandler {
      def hide(window: ee.ui.display.Window): Unit = ???
      def show(window: ee.ui.display.Window): Unit = ???
    }
  }
}