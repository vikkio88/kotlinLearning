package org.vikkio.models

import com.github.f4b6a3.ulid.UlidCreator
import kotlinx.serialization.Serializable
import org.vikkio.models.enums.UserType

@Serializable
data class User(
    val fullName: String,
    val wallet: Money? = null,
    val role: UserType = UserType.USER,
    val id: String = "",
    val username: String = ""
)

object UserFactory {
    fun makeUser(fullName: String, wallet: Money? = null): User {
        return User(
            fullName = fullName.trim(),
            wallet = wallet,
            id = UlidCreator.getUlid().toString(),
            username = fullName.lowercase().split(" ").joinToString(".").trim('.')
        )
    }

    fun makeAdmin(username: String = "admin", id: String = "admin"): User {
        return User(id = id, role = UserType.ADMIN, fullName = username, username = username)
    }

}