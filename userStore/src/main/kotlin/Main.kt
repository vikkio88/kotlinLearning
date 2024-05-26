package org.vikkio

import org.vikkio.app.App
import sun.misc.Signal
import kotlin.system.exitProcess

fun main() {
    val app = App()

    Signal.handle(Signal("INT")){
        println("\n\nReceived SIGINT, terminating app...")
        println("All Done. Bye!\n\n")
        exitProcess(0)
    }
    app.run()
}