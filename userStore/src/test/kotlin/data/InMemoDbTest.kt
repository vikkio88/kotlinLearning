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

    @Test
    fun passwordJourney() {
        val db = InMemoDb()
        val user = User("Mariano Marione", Money(1000, Currency.EURO))
        db.addUser(user)

        assertFalse(db.login(user.id, "PASSWORD"))
        db.setUserPassword(user.id, "PASSWORD")
        assertTrue(db.login(user.id, "PASSWORD"))
        assertFalse(db.setUserPassword(user.id, "PASSWORD1", "WRONG"))
        assertFalse(db.login(user.id, "WRONG"))
        assertTrue(db.setUserPassword(user.id, "PASSWORD1", "PASSWORD"))
        assertFalse(db.login(user.id, "PASSWORD"))
        assertTrue(db.login(user.id, "PASSWORD1"))
    }
}