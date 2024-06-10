package org.vikkio.libs

import java.nio.charset.StandardCharsets
import java.util.*
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

fun generateAESKeyFromString(keyString: String): SecretKey {
    val paddedKeyString = keyString.padEnd(16 * ((keyString.length / 16) + 1), ' ')
    val keyBytes = paddedKeyString.toByteArray(StandardCharsets.UTF_8)
    return SecretKeySpec(keyBytes, "AES")
}

class Crypto(passkey: String) {
    private val secret: SecretKey = generateAESKeyFromString(passkey)
    fun encrypt(value: String): String {
        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        val ivParameterSpec = IvParameterSpec(ByteArray(16)) // Use a secure IV in production
        cipher.init(Cipher.ENCRYPT_MODE, secret, ivParameterSpec)
        val bytes = cipher.doFinal(value.toByteArray())
        return Base64.getEncoder().encodeToString(bytes)
    }

    fun decrypt(encrypted: String): String {
        val decodedBytes = Base64.getDecoder().decode(encrypted)
        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        val ivParameterSpec = IvParameterSpec(ByteArray(16)) // Use the same IV as used in encryption
        cipher.init(Cipher.DECRYPT_MODE, secret, ivParameterSpec)
        return String(cipher.doFinal(decodedBytes), StandardCharsets.UTF_8)
    }
}