package org.vikkio.exercises

import org.vikkio.interfaces.Exercise
import org.vikkio.exercises.employee.Employee


class Classes1 : Exercise {
    override val title: String
        get() = "Classes 1 - Employee"

    //Define a data class Employee with two properties: one for a name, and another for a salary.
    // Make sure that the property for salary is mutable, otherwise you won’t get a salary
    // boost at the end of the year!
    // The main function demonstrates how you can use this data class.
    override fun body() {
        val emp = Employee("Mary", 20)
        println(emp)
        emp.salary += 10
        println(emp)
    }

}