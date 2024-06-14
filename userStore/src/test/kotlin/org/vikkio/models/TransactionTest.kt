package org.vikkio.models

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.vikkio.libs.JSON
import org.vikkio.models.enums.Currency
import org.vikkio.org.vikkio.models.Transaction
import org.vikkio.org.vikkio.models.TransactionFactory


class TransactionTest {

    @Test
    fun testTransactionLog() {
        val tl = TransactionFactory.make("fromId", "toId", Money(100, Currency.EURO))
        val j = JSON.stringify(tl)
        val tl2 = JSON.parse<Transaction>(j)

        assertEquals(tl, tl2)
    }

}
