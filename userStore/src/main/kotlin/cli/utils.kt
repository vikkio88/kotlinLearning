package org.vikkio.cli

fun banner() {
    println("\nKotlin User Store Project\n")
}

fun input(prompt: String = "> "): String? {
    print(prompt)
    return readlnOrNull()
}

fun <T> getFromList(list: List<T>): T? {
    val count = list.count()
    if (count < 1) {
        return null
    }
    if (count == 1) {
        println("only 1 item to select, selected")
        return list[0]
    }

    list.forEachIndexed { i: Int, item: T ->
        println("${i + 1} - ${item.toString()}")
    }

    var num: Int? = null
    while ((num == null) || (num < 1 || num > list.count())) {
        if (num != null) {
            println("you typed '$num' that is not a valid selection.")
        }
        num = inputNumber("[1-${list.count()}] > ")
    }

    return list[num - 1]

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