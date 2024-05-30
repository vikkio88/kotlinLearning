package org.vikkio.exercises.employee

import kotlin.random.Random

class RandomEmployeeGenerator(var minSalary: Int, var maxSalary: Int) {
    private val names = listOf("Mario", "Maurizio", "Mariano", "Manlio", "Melo")
    fun generateEmployee(): Employee {
        return Employee(
            names.random(),
            Random.nextInt(from = minSalary, until = maxSalary)
        )
    }
}