package org.vikkio.models

const val MULTIPLIER:Float = 100.0f


class Money(private val value: Int, private val currency: Currency){
    override fun toString(): String {
        return "${"%.2f".format(value / MULTIPLIER)}$currency"
    }

    operator fun plus(other: Money): Money {
        if (other.currency != this.currency){
            throw Exception("Invalid Operation")
        }

        return Money(this.value + other.value, this.currency)
    }
}