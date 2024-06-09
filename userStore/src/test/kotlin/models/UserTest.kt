package models

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.vikkio.libs.JSON
import org.vikkio.models.AccountFactory
import org.vikkio.models.Money
import org.vikkio.models.User
import org.vikkio.models.UserFactory
import org.vikkio.models.enums.Currency
import kotlin.test.assertContains

class UserTest {

    @Test
    fun userFactory() {
        var user = UserFactory.makeUser("Mario Rossi")
        assertEquals("mario.rossi", user.username)

        user = UserFactory.makeUser("Ciccio ")
        assertEquals("ciccio", user.username)

        user = UserFactory.makeUser("Ciccio")
        assertEquals("ciccio", user.username)

        user = UserFactory.makeUser("Ciccio X")
        assertEquals("ciccio.x", user.username)

        // with account
        val mainAccount = AccountFactory.makeAccount(Money(100, Currency.EURO))
        user = UserFactory.makeUser("Ciccio Main", mainAccount)
        assertEquals("ciccio.main", user.username)
        assertNotNull(user.getMainAccount())
        assertEquals("1.00€", user.getMainAccount()?.balance.toString())
    }

    @Test
    fun getId() {
        assertNotEquals(UserFactory.makeUser("Mario").id, UserFactory.makeUser("Mario").id)
    }

    @Test
    fun getFullName() {
        assertEquals("Mario Biondo", UserFactory.makeUser(" Mario Biondo ").fullName)
    }

    @Test
    fun toJson() {
        val user = UserFactory.makeUser("Lorenzo Cacca", Money(100, Currency.US_DOLLAR))
        val jsonString = JSON.stringify(user)
        assertTrue(jsonString.isNotEmpty())
        assertContains(jsonString, user.id)
        assertContains(jsonString, user.username)
        assertContains(jsonString, user.role.toString())
        val otherUser = JSON.parse<User>(jsonString)
        assertEquals(otherUser, user)
    }
}