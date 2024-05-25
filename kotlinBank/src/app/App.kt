package app

import cli.banner
import cli.cls
import cli.menu

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