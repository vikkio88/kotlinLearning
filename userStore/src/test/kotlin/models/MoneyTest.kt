package models

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.assertThrows
import org.vikkio.models.Currency
import org.vikkio.models.Money
import java.lang.UnsupportedOperationException

class MoneyTest {

    @Test
    fun testToString() {
        assertEquals("10.00$", "${Money(1000, Currency.US_DOLLAR)}")
    }

    @Test
    fun testOperations(){
        assertEquals("25.55$", "${Money(1055, Currency.US_DOLLAR) + Money(1500, Currency.US_DOLLAR)}")
        assertThrows<UnsupportedOperationException> { Money(100, Currency.EURO) + Money(7000, Currency.GB_POUND) }

        val oneEuro = Money(100, Currency.EURO)
        val twoEuros = Money(200, Currency.EURO)
        val otherOneEuro = Money(100, Currency.EURO)

        assertEquals(oneEuro, otherOneEuro)
        assertEquals(twoEuros - oneEuro, oneEuro)
        assertThrows<UnsupportedOperationException> { oneEuro - twoEuros }
        assertThrows<UnsupportedOperationException> { Money(60000, Currency.US_DOLLAR) - oneEuro }
    }

    @Test
    fun tetPercentageChange(){
        val hundredPounds = Money(10000,Currency.GB_POUND)
        assertEquals(hundredPounds.adjustPercentage(1.0f), Money(20000, Currency.GB_POUND) )
        assertEquals(hundredPounds.adjustPercentage(-.5f), Money(5000, Currency.GB_POUND) )
    }
}