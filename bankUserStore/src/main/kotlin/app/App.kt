package bankUserStore.app

import bankUserStore.cli.banner
import bankUserStore.cli.cls
import bankUserStore.cli.menu

class App {
    fun run(){
        banner()
        var choice:String? = null
        while (choice!="q") {
            if (choice!= null){
                cls()
                println("\nYou pressed $choice, Try again...\n")
            }
            menu()

            choice = readlnOrNull()?.lowercase()
        }

        println()
    }
}