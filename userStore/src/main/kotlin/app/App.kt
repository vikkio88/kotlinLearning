package org.vikkio.app

import org.vikkio.app.methods.Methods
import org.vikkio.app.methods.menuMethodMap
import org.vikkio.app.methods.methodLabels
import org.vikkio.app.methods.methodMap
import org.vikkio.cli.banner
import org.vikkio.cli.cls
import org.vikkio.cli.enterToContinue
import org.vikkio.cli.input
import org.vikkio.models.User
import sun.misc.Signal
import kotlin.system.exitProcess

val defaultCleanup = {
    println("All Done. Bye!\n\n")
}

class App(private val cleanup: () -> Unit = defaultCleanup) {
    val db: MutableCollection<User> = mutableListOf()

    fun run() {
        setup()

        cls()
        banner()
        var choice: String? = null
        var selectedMethod = Methods.NO_METHOD
        while (selectedMethod != Methods.QUIT) {
            if (choice != null) {
                cls()
            }
            if ((choice != null && selectedMethod == Methods.NO_METHOD)) {
                println("\nYou typed: '$choice', no method with that, Try again...\n")
            }


            menu()

            choice = input()?.lowercase()
            selectedMethod = parseChoice(choice)

            if (selectedMethod in methodMap) {
                methodMap[selectedMethod]?.let { it(db) }
                enterToContinue()
            }

        }

        println("Exiting.")
        cleanup()
    }

    private fun menu() {
        for (e in menuMethodMap) {
            val key = e.key[0]
            val method = e.value

            println("$key - ${methodLabels[method]}")
        }
    }

    private fun parseChoice(choice: String?): Methods {
        for (e in menuMethodMap) {
            if (choice in e.key) {
                return e.value
            }
        }

        return Methods.NO_METHOD
    }

    private fun setup() {
        Signal.handle(Signal("INT")) { _ ->
            println("\n\nReceived SIGINT, terminating app...")
            cleanup()
            exitProcess(0)
        }
    }
}