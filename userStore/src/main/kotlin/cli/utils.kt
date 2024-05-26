package org.vikkio.cli

fun banner() {
    println("\nKotlin User Store Project\n")
}

fun menu() {
    println("\nMenu\n 1. Create new User.\n 2. Show Users.\n\n q. Quit")
}

 fun cls() {
    print("\u001b[H\u001b[2J")
}