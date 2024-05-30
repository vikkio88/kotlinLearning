package org.vikkio

import org.vikkio.app.App
import org.vikkio.models.Currency
import org.vikkio.models.Money
import sun.misc.Signal
import kotlin.system.exitProcess


fun main() {

    val m = Money(100, Currency.EURO)


    println(m.toJson())
    val app = App()

    Signal.handle(Signal("INT")) {
        println("\n\nReceived SIGINT, terminating app...")
        println("All Done. Bye!\n\n")
        exitProcess(0)
    }
    app.run()
}