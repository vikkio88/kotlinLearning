package org.vikkio.exercises

import org.vikkio.interfaces.Exercise

class Collections3 : Exercise {
    override val title: String
        get() = "Collection 3 - Maps"

    /**
     * Define a map that relates integer numbers from 1 to 3 to their corresponding spelling.
     * Use this map to spell the given number.
     */
    override fun body() {
        val spelling = mapOf<Int,String>(1 to "One", 2 to "Two", 3 to "Three")
        for (i in 1..3){
            println("The spelling of '$i' is '${spelling[i]}'")
        }

    }
}