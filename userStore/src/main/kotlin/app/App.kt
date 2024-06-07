package org.vikkio.app

import org.vikkio.app.methods.*
import org.vikkio.cli.banner
import org.vikkio.cli.cls
import org.vikkio.cli.enterToContinue
import org.vikkio.cli.input
import org.vikkio.data.InMemoDb
import org.vikkio.models.Money
import org.vikkio.models.UserFactory
import org.vikkio.models.enums.Currency
import sun.misc.Signal
import kotlin.system.exitProcess

val defaultCleanup = {
    println("All Done. Bye!\n\n")
}

class App(private val cleanup: () -> Unit = defaultCleanup) {
    private var context: Context = Context(InMemoDb())

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

            choice = input()?.lowercase()
            selectedMethod = parseChoice(choice)

            if (selectedMethod.body != null) {
                selectedMethod.body!!.invoke(context)
                enterToContinue()
            }
        }



        println("Exiting.")
        cleanup()
    }

    private fun state() {
        val user = context.getLoggedInUser()
        println(
            when (context.getState()) {
                AppState.LoggedOut -> ""
                AppState.AdminLoggedIn -> "[Admin] ${user?.username}."
                AppState.UserLoggedIn -> "Logged in as ${user?.username}.\nBalance: ${user?.wallet ?: "*No Wallet*"}"
            }
        )
    }

    private fun getMenu(): Map<Array<String>, Methods> {
        return stateToMethods[context.getState()] ?: loginMenuMap
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
            cleanup()
            exitProcess(0)
        }
    }

    private fun boot() {
        setup()
        val admin = UserFactory.makeAdmin("admin")
        context.db.addUser(admin)
        context.db.resetUserPassword(admin.id, "password")

        val testUserNoWallet = UserFactory.makeUser("mario nowallet")
        val testUserWallet = UserFactory.makeUser("mario wallet", Money(100, Currency.EURO))
        context.db.addUser(testUserNoWallet)
        context.db.resetUserPassword(testUserNoWallet.id, "mario")
        context.db.addUser(testUserWallet)
        context.db.resetUserPassword(testUserWallet.id, "mario")
    }
}