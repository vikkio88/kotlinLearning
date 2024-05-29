package org.vikkio.exercises

import org.vikkio.abstracts.Exercise
import kotlin.Unit

class Lambdas2:Exercise() {
    override val title: String
        get() = "Functions - Lambdas 2"

    // Write a function that takes an Int value and an action (a function with type () -> Unit)
    // which then repeats the action the given number of times.
    // Then use this function to print “Hello” 5 times.
    override fun body() {
        val repeater = {times: Int, action: ()-> Unit -> (1..times).forEach{ _ ->action()}}

        // This -> repeater(5, { println("Hello") })
        // and this down are the same
        repeater(5) { println("Hello") }
    }
}