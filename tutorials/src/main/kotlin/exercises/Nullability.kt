package org.vikkio.exercises

import org.vikkio.interfaces.Exercise
import org.vikkio.exercises.employee.Employee

class Nullability : Exercise {
    override val title: String
        get() = "Nullability"

    private fun employeeById(id: Int) = when (id) {
        1 -> Employee("Mary", 20)
        2 -> null
        3 -> Employee("John", 21)
        4 -> Employee("Ann", 23)
        else -> null
    }

    private fun salaryById(id: Int) = employeeById(id)?.salary ?: 0


    // You have the employeeById function that gives you access to a database of employees
    // of a company. Unfortunately, this function returns a value of the Employee?
    // type, so the result can be null.
    // Your goal is to write a function that returns the salary of an employee when their
    // id is provided, or 0 if the employee is missing from the database.
    override fun body() {
        println((1..5).sumOf { id -> salaryById(id) })
    }
}