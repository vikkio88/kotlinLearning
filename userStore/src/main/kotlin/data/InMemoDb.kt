package org.vikkio.data

import org.vikkio.models.AccountFactory
import org.vikkio.models.Money
import org.vikkio.models.User

class InMemoDb : IDb {
    private val users = mutableMapOf<String, User>()
    private val usernames = mutableMapOf<String, String>()
    private val passwords = mutableMapOf<String, String>()

    override fun addUser(user: User): Boolean {
        if (user.username in usernames) {
            return false
        }

        users[user.id] = user
        usernames[user.username] = user.id

        return true
    }

    override fun getUsers(): Iterable<User> {
        return users.values
    }

    override fun getUserById(id: String): User? {
        return users[id]
    }

    override fun tryUpdateWallet(userId: String, amount: Money): Boolean {
        val user = getUserById(userId) ?: return false

        try {
            val mainAccount = user.accounts.firstOrNull() ?: AccountFactory.makeEmpty(amount.currency)
            //TODO fix this +
            val newUser = user.copy(accounts = mainAccount + amount)
            users[userId] = newUser
        } catch (e: Exception) {
            return false
        }

        return true
    }

    override fun resetUserPassword(userId: String, newPassword: String): Boolean {
        passwords[userId] = newPassword
        return true
    }

    override fun setUserPassword(userId: String, newPassword: String, oldPassword: String): Boolean {
        val oldPwd = passwords[userId]
        if (oldPwd != oldPassword) {
            return false
        }

        passwords[userId] = newPassword

        return true
    }

    override fun tryLogin(username: String, password: String): User? {
        val userId = usernames[username] ?: return null

        val pwd = passwords[userId] ?: return null

        return if (pwd == password) users[userId] else null
    }
}