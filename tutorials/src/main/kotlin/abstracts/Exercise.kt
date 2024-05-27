package org.vikkio.abstracts

abstract class Exercise {
    abstract val title:String

    fun run(){
        println("Running '$title'")
        body()
        println("finished running: '$title'\n")
    }
    protected abstract fun body()
}