package org.vikkio.exercises

import org.vikkio.interfaces.Exercise
import org.vikkio.exercises.employee.RandomEmployeeGenerator

class Classes2 : Exercise {
    override val title: String
        get() = "Classes 2 - Employee Generator"

    // To test your code, you need a generator that can create random employees.
    // Define a class with a fixed list of potential names (inside the class body),
    // and that is configured by a minimum and maximum salary (inside the class header).
    // Once again, the main function demonstrates how you can use this class.
    override fun body() {
        val empGen = RandomEmployeeGenerator(10, 30)
        println("Before the update")
        println(empGen.generateEmployee())
        println(empGen.generateEmployee())
        println(empGen.generateEmployee())
        empGen.minSalary = 50
        empGen.maxSalary = 100
        println("\nAfter the update")
        println(empGen.generateEmployee())
    }
}