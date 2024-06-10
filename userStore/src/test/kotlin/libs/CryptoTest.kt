package libs

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.vikkio.libs.Crypto

class CryptoTest {

    @Test
    fun encryptFullJourney() {
        val crypto = Crypto("password")

        val encrypted = crypto.encrypt("sometext")
        assertNotEquals(encrypted, "sometext")
        assertEquals("sometext", crypto.decrypt(encrypted))
    }
}