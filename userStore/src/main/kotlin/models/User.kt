package org.vikkio.models

import com.github.f4b6a3.ulid.UlidCreator
import kotlinx.serialization.*
import org.vikkio.models.enums.UserType

@Serializable
data class User(
    val fullName: String,
    //This has to be MutableList instead of IFace as MutableCollection is not Serializable
    val accounts: MutableList<Account> = mutableListOf(),
    val role: UserType = UserType.USER,
    val id: String = "",
    val username: String = ""
) {
    @Transient
    var selectedAccount: Account? = null
        get() = field ?: accounts.firstOrNull()

    fun addAccount(account: Account) {
        accounts.add(account)
    }

    override fun toString(): String {
        val accountsCount = accounts.count()
        return "$username ($fullName). $accountsCount account${if (accountsCount > 1) "s" else ""}"
    }
}

object UserFactory {
    fun makeUser(fullName: String, wallet: Money? = null): User {
        return User(
            fullName = fullName.trim(),
            accounts = if (wallet != null) mutableListOf(AccountFactory.makeAccount(wallet)) else mutableListOf(),
            id = UlidCreator.getUlid().toString(),
            username = fullName.lowercase().split(" ").joinToString(".").trim('.')
        )
    }

    fun makeUser(fullName: String, mainAccount: Account): User {
        return User(
            fullName = fullName.trim(),
            accounts = mutableListOf(mainAccount),
            id = UlidCreator.getUlid().toString(),
            username = fullName.lowercase().split(" ").joinToString(".").trim('.')
        )
    }

    fun makeAdmin(username: String = "admin", id: String = "admin"): User {
        return User(id = id, role = UserType.ADMIN, fullName = username, username = username)
    }

}