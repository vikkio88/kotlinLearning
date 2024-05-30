package org.vikkio.exercises

import org.vikkio.interfaces.Exercise

class ControlFlows3 : Exercise {
    override val title: String
        get() = "Control Flows 3 - FizzBuzz"

    override fun body() {
        for (i in 1..100) {
            println("- Number $i becomes ${fizzBuzz(i)}")
        }
    }

    private fun fizzBuzz(i: Int): String {
        val by3 = isDivBy(i, 3)
        val by5 = isDivBy(i, 5)
        if (!by3 && !by5) {
            return "$i"
        }

        if (by3 && by5) {
            return "fizzbuzz"
        }

        if (by3) {
            return "fizz"
        }

        return "buzz"
    }

    private fun isDivBy(i: Int, div: Int): Boolean {
        return i % div == 0
    }
}