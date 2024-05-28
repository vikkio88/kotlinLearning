package org.vikkio.exercises

import org.vikkio.abstracts.Exercise
import kotlin.system.measureNanoTime
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class ControlFlows4 : Exercise() {
    override val title: String
        get() = "Control Flows 4 - Words with L"

    /**
     * You have a list of words. Use for and if to print only the words that start with the letter l.
     */
    override fun body() {
        val words = listOf("dinosaur", "limousine", "magazine", "language")
        println("Printing word that starts with L for/if approach")
        var elapsed = measureNanoTime {
            for (w in words) {
                if (w.startsWith('l')) {
                    println(w)
                }
            }
        }
        println("took: ${elapsed.toDuration(DurationUnit.NANOSECONDS)}")

        println("Printing word that starts with L - functional approach")
        elapsed = measureNanoTime {
            words.filter { it.startsWith('l') }.map(::println)
        }
        println("took: ${elapsed.toDuration(DurationUnit.NANOSECONDS)}")
    }

}