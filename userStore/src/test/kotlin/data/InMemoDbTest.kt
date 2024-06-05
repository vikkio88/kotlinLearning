package data

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.vikkio.data.InMemoDb
import org.vikkio.models.User

class InMemoDbTest {

    @Test
    fun addGetUsers() {
        val db = InMemoDb()
        db.addUser(User("mario"))
        assertEquals(1, db.getUsers().count())
        db.addUser(User("marione"))
        assertEquals(2, db.getUsers().count())
    }

    @Test
    fun passwordJourney() {
        val db = InMemoDb()
        val user = User("Mariano Marione")
        db.addUser(user)

        assertNull(db.tryLogin(user.id, "PASSWORD"))
        db.resetUserPassword(user.id, "PASSWORD")
        assertNotNull(db.tryLogin(user.id, "PASSWORD"))
        assertFalse(db.setUserPassword(user.id, "PASSWORD1", "WRONG"))
        assertNull(db.tryLogin(user.id, "WRONG"))
        assertTrue(db.setUserPassword(user.id, "PASSWORD1", "PASSWORD"))
        assertNull(db.tryLogin(user.id, "PASSWORD"))
        assertNotNull(db.tryLogin(user.id, "PASSWORD1"))
        db.resetUserPassword(user.id, "MARIOPASSWORD")
        assertNull(db.tryLogin(user.id, "PASSWORD1"))
        assertNotNull(db.tryLogin(user.id, "MARIOPASSWORD"))
    }
}