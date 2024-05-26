import app.App
import sun.misc.Signal
import kotlin.system.exitProcess

fun main() {
   val app = App()

   Signal.handle(Signal("INT")){
      println("\nReceived SIGINT, terminating app.")
      exitProcess(0);
   }
   app.run()
}