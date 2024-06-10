package org.vikkio.data

import org.vikkio.libs.JSON
import org.vikkio.models.AccountFactory
import org.vikkio.models.Money
import org.vikkio.models.User
import org.vikkio.models.UserFactory
import org.vikkio.models.enums.Currency
import java.io.File

const val DB_FILENAME = "test.db.json"
const val DB_PWD_FILENAME = "test.pwd.db.json"

class InMemoDb : IDb {
    private var users = mutableMapOf<String, User>()
    private val usernames = mutableMapOf<String, String>()
    private val passwords = mutableMapOf<String, String>()


    override fun boot() {
        if (File(DB_FILENAME).exists()) {
            users = JSON.parse(File(DB_FILENAME).readText()) ?: mutableMapOf()
            val persistedPasswords = loadPassword()
            passwords.clear()
            usernames.clear()
            for ((id, u) in users) {
                usernames[u.username] = id
                passwords[id] = persistedPasswords?.getOrDefault(id, "password") ?: "password"
            }

            return
        }

        // Defaults
        defaultUsers()
    }

    private fun loadPassword(): MutableMap<String, String>? {
        if (!File(DB_PWD_FILENAME).exists()) {
            return null
        }

        return JSON.parse(File(DB_PWD_FILENAME).readText())
    }

    private fun defaultUsers() {
        val admin = UserFactory.makeAdmin("admin")
        addUser(admin)
        resetUserPassword(admin.id, "password")

        val testUserNoWallet = UserFactory.makeUser("mario nowallet")
        val testUserWallet = UserFactory.makeUser("mario wallet", Money(100, Currency.EURO))
        addUser(testUserNoWallet)
        resetUserPassword(testUserNoWallet.id, "mario")
        addUser(testUserWallet)
        resetUserPassword(testUserWallet.id, "mario")
    }

    override fun cleanup() {
        persist()
        // maybe close connections and stuff
    }

    private fun persist() {
        //save to file
        File(DB_FILENAME).writeText(JSON.stringify(users))
        File(DB_PWD_FILENAME).writeText(JSON.stringify(passwords))
    }

    override fun addUser(user: User): Boolean {
        if (user.username in usernames) {
            return false
        }

        users[user.id] = user
        usernames[user.username] = user.id

        return true
    }

    override fun deleteUser(userId: String): Boolean {
        val user = getUserById(userId) ?: return false

        usernames.remove(user.username)
        passwords.remove(userId)
        users.remove(userId)

        return true
    }

    override fun getUsers(): Iterable<User> {
        return users.values
    }

    override fun getUserById(id: String): User? {
        return users[id]
    }

    override fun tryUpdateWallet(userId: String, amount: Money, accountId: String?): Boolean {
        val user = getUserById(userId) ?: return false

        try {
            val selectedAccount = user.selectedAccount ?: AccountFactory.makeEmpty(amount.currency)
            val updatedAccount = selectedAccount.copy(balance = selectedAccount.balance + amount)

            val newAccounts = user.accounts.filter { it.id != updatedAccount.id }.toMutableList()
            newAccounts.add(updatedAccount)

            val newUser = user.copy(accounts = newAccounts)
            newUser.selectedAccount = updatedAccount
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

    override fun updateUser(user: User): Boolean {
        users[user.id] = user

        return true
    }
}