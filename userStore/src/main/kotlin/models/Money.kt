package org.vikkio.models

const val MULTIPLIER:Float = 100.0f


class Money(private val value: Int, private val currency: Currency){
    override fun toString(): String {
        return "${"%.2f".format(value / MULTIPLIER)}$currency"
    }
}