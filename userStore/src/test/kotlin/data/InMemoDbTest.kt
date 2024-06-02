package data

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.vikkio.data.InMemoDb
import org.vikkio.models.Currency
import org.vikkio.models.Money
import org.vikkio.models.User

class InMemoDbTest {

    @Test
    fun addGetUsers() {
        val db = InMemoDb()
        db.addUser(User("mario", Money(10, Currency.EURO)))
        assertEquals(1, db.getUsers().count())
        db.addUser(User("marione", Money(2000, Currency.EURO)))
        assertEquals(2, db.getUsers().count())
    }
}