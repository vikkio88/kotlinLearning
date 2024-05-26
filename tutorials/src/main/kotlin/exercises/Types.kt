package org.vikkio.exercises

import org.vikkio.abstracts.Exercise

class Types: Exercise() {
    override val title: String
        get() = "Types"

    override fun body() {

            val a: Int = 1000
            val b: String = "log message"
            val c: Double = 3.14
            val d: Long = 100_000_000_000
            val e: Boolean = false
            val f: Char = '\n'

    }
}