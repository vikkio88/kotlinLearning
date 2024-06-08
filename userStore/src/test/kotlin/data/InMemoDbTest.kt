package data

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.vikkio.data.InMemoDb
import org.vikkio.models.UserFactory

class InMemoDbTest {

    @Test
    fun addGetUsers() {
        val db = InMemoDb()
        db.addUser(UserFactory.makeUser("mario"))
        assertEquals(1, db.getUsers().count())
        db.addUser(UserFactory.makeUser("marione"))
        assertEquals(2, db.getUsers().count())
    }

    @Test
    fun passwordJourney() {
        val db = InMemoDb()
        val user = UserFactory.makeUser("Mario")
        db.addUser(user)
        val username = user.username
        val userId = user.id
        assertNull(db.tryLogin(username, "PASSWORD"))
        db.resetUserPassword(userId = userId, "PASSWORD")
        assertNotNull(db.tryLogin(username, "PASSWORD"))
        assertFalse(db.setUserPassword(userId = userId, "PASSWORD1", "WRONG"))
        assertNull(db.tryLogin(username, "WRONG"))
        assertTrue(db.setUserPassword(userId = userId, "PASSWORD1", "PASSWORD"))
        assertNull(db.tryLogin(username, "PASSWORD"))
        assertNotNull(db.tryLogin(username, "PASSWORD1"))
        db.resetUserPassword(userId = userId, "MARIOPASSWORD")
        assertNull(db.tryLogin(username, "PASSWORD1"))
        assertNotNull(db.tryLogin(username, "MARIOPASSWORD"))
    }
}