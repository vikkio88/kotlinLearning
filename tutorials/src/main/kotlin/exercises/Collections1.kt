package org.vikkio.exercises

import org.vikkio.interfaces.Exercise

class Collections1 : Exercise {
    override val title: String
        get() = "Collections 1 - List"

    // You have a list of “green” numbers and a list of “red” numbers.
    // Complete the code to print how many numbers there are in total.
    override fun body() {
        val greenNumbers = listOf(1, 4, 23)
        val redNumbers = listOf(17, 2)
        // Write your code here

        println("total: ${greenNumbers.count() + redNumbers.count()}")
    }
}