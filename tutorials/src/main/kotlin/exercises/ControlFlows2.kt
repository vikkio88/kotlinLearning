package org.vikkio.exercises

import org.vikkio.abstracts.Exercise

const val MAX_PIZZA_SLICES = 8

class ControlFlows2 : Exercise() {
    override val title: String
        get() = "Control Flows 2"

    override fun body() {
        var slices = 0
        while (slices < MAX_PIZZA_SLICES - 1) {
            println("There are ${if (slices > 0) "only $slices slices" else "no slices"} of pizza :(")
            slices++
        }
        println("Hooray, a full pizza with $slices slices, done in a while loop.")

        slices = 0
        do {
            println("There are ${if (slices > 0) "only $slices slices" else "no slices"} of pizza :(")
            slices++
        } while (slices < MAX_PIZZA_SLICES)
        println("Hooray, a full pizza with $slices slices, done in a do while.")

    }
}