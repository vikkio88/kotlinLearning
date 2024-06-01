package org.vikkio.cli

fun banner() {
    println("\nKotlin User Store Project\n")
}

fun input(prompt: String = "> "): String? {
    print(prompt)
    return readlnOrNull()
}

fun enterToContinue() {
    input("\n\t[Enter] to continue...\n")
}

fun inputNumber(prompt: String = "(num)> "): Int? {
    while (true) {
        val num = input(prompt)
        if (num == "quit") {
            return null
        }

        try {
            return num?.toInt()
        } catch (e: Exception) {
            println("'$num' is not a number, try again.")
            println("type [quit] to set 0")
        }
    }
}

fun cls() {
    print("\u001b[H\u001b[2J")
}