package models

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.vikkio.models.Currency
import org.vikkio.models.Money

class MoneyTest {

    @Test
    fun testToString() {
        assertEquals("10.00$", "${Money(1000, Currency.US_DOLLAR)}")
    }
}