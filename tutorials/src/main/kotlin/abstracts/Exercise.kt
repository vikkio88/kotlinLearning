package org.vikkio.abstracts

abstract class Exercise {
    abstract val title:String

    fun run(){
        println("Running '$title'")
        body()
        println("\nfinished running: '$title'")
    }
    protected abstract fun body()
}