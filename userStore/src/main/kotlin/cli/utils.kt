package cli

fun banner() {
    println("Kotlin User Store Project")
}

fun menu() {
    println("Menu\n 1. Create new User.\n 2. Show Users.\n\n q. Quit")
}

 fun cls() {
    print("\u001b[H\u001b[2J")
}