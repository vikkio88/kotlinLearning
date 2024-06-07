package models

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.assertThrows
import org.vikkio.models.enums.Currency
import org.vikkio.models.Money
import java.lang.UnsupportedOperationException

class MoneyTest {

    @Test
    fun testAttributes() {
        assertEquals(10.0f, (Money(1000, Currency.EURO)).amount)
        val oneEuro = Money(100, Currency.EURO)

        assertEquals("euro", oneEuro.currency.getName())
        assertEquals(1.0f, oneEuro.currency.conversionRate)
    }

    @Test
    fun testToString() {
        assertEquals("10.00$", "${Money(1000, Currency.US_DOLLAR)}")
    }

    @Test
    fun testOperations() {
        assertEquals("25.55$", "${Money(1055, Currency.US_DOLLAR) + Money(1500, Currency.US_DOLLAR)}")
        assertThrows<UnsupportedOperationException> { Money(100, Currency.EURO) + Money(7000, Currency.GB_POUND) }

        val oneEuro = Money(100, Currency.EURO)
        val twoEuros = Money(200, Currency.EURO)
        val otherOneEuro = Money(100, Currency.EURO)

        assertEquals(oneEuro, otherOneEuro)
        assertEquals(twoEuros - oneEuro, oneEuro)
        assertThrows<UnsupportedOperationException> { oneEuro - twoEuros }
        assertThrows<UnsupportedOperationException> { Money(60000, Currency.US_DOLLAR) - oneEuro }

        assertEquals("-1.00€", (otherOneEuro * -1).toString())
    }

    @Test
    fun testPercentageChange() {
        val hundredPounds = Money(10000, Currency.GB_POUND)
        assertEquals(hundredPounds.adjustPercentage(1.0f), Money(20000, Currency.GB_POUND))
        assertEquals(hundredPounds.adjustPercentage(-.5f), Money(5000, Currency.GB_POUND))
    }

    @Test
    fun testConversion() {
        val oneEuro = Money(100, Currency.EURO)
        assertEquals(oneEuro, oneEuro.convert(Currency.EURO))
        assertNotEquals(oneEuro, oneEuro.convert(Currency.US_DOLLAR))
        val oneDollar = Money(100, Currency.US_DOLLAR)
        assertEquals("0.92€", oneDollar.convert(Currency.EURO).toString())

        val onePound = Money(100, Currency.GB_POUND)
        assertEquals("1.27$", onePound.convert(Currency.US_DOLLAR).toString())

        assertEquals("0.78£", oneDollar.convert(Currency.GB_POUND).toString())
    }
}