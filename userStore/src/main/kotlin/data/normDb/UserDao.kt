package org.vikkio.data.normDb

import org.vikkio.models.Account
import org.vikkio.models.User
import org.vikkio.models.enums.UserType
import javax.persistence.Id
import javax.persistence.Table

@Table(name = "Users")
class UserDao(
    @Id
    val id: String = "",
    val fullName: String,
    val role: UserType = UserType.USER,
    val username: String = ""
) {
    init {
        println("init")
    }

    companion object {
        fun from(user: User): UserDao {
            return UserDao(
                id = user.id,
                fullName = user.fullName,
                role = user.role,
                username = user.username,
            )
        }
    }

    fun toUser(): User {
        return User(
            id = id,
            fullName = fullName,
            role = role,
            username = username,
        )

    }
}