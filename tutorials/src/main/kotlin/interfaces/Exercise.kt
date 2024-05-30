package org.vikkio.interfaces

interface Exercise {
    val title: String

    fun run() {
        println("Running '$title'")
        body()
        println("finished running: '$title'\n")
    }

    fun body()
}