package org.vikkio.app

import org.vikkio.cli.banner
import org.vikkio.cli.cls
import org.vikkio.cli.menu

class App {
    fun run(){
        cls()
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