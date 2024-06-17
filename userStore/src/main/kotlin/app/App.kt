package org.vikkio.app

import org.vikkio.app.methods.*
import org.vikkio.cli.banner
import org.vikkio.cli.cls
import org.vikkio.cli.enterToContinue
import org.vikkio.cli.input
import org.vikkio.data.exposedDb.ExposedSqliteDb
import org.vikkio.libs.Crypto
import sun.misc.Signal
import kotlin.system.exitProcess

val defaultCleanup = { ctx: Context ->
    ctx.db.cleanup()
    println("All Done. Bye!\n\n")
}

class App(aesSecret: String, private val cleanup: (Context) -> Unit = defaultCleanup) {
    private var context: Context = Context(ExposedSqliteDb(Crypto(aesSecret)))

    fun run() {
        boot()

        cls()
        banner()
        var choice: String? = null
        var selectedMethod = Methods.NO_METHOD
        while (selectedMethod != Methods.QUIT) {
            if (choice != null) {
                cls()
            }
            state()
            if ((choice != null && selectedMethod == Methods.NO_METHOD)) {
                println("\n${if (choice.isEmpty()) "You did not type anything." else "You typed: '$choice'"}, no method with that, Try again...\n")
            }


            menu()

            choice = input()?.lowercase()?.trim()
            selectedMethod = parseChoice(choice)

            if (selectedMethod.body != null) {
                selectedMethod.body!!.invoke(context)
                enterToContinue()
            }
        }

        println("Exiting.")
        cleanup(context)
    }

    private fun state() {
        val user = context.getLoggedInUser()
        println(
            when (context.state) {
                AppState.LoggedOut -> ""
                AppState.AdminLoggedIn -> "[Admin] ${user?.username}."
                AppState.UserLoggedIn -> "Logged in as ${user?.username}.\nBalance: ${user?.selectedAccount ?: "*No Accounts*"}"
            }
        )
    }

    private fun getMenu(): Map<Array<String>, Methods> {
        return stateToMethods[context.state] ?: loginMenuMap
    }

    private fun menu() {
        for (e in getMenu()) {
            val key = e.key[0]
            val method = e.value

            println("$key - ${method.label}")
        }
    }

    private fun parseChoice(choice: String?): Methods {
        for (e in getMenu()) {
            if (choice in e.key) {
                return e.value
            }
        }

        return Methods.NO_METHOD
    }

    private fun setup() {
        Signal.handle(Signal("INT")) { _ ->
            println("\n\nReceived SIGINT, terminating app...")
            cleanup(context)
            exitProcess(0)
        }
    }

    private fun boot() {
        setup()
        context.db.boot()
    }
}