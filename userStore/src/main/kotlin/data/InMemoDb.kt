package org.vikkio.data

import org.vikkio.libs.Crypto
import org.vikkio.libs.JSON
import org.vikkio.models.*
import org.vikkio.models.enums.Currency
import java.io.File

const val DB_FILENAME = "test.db.json"
const val DB_PWD_FILENAME = "test.pwd.db.json"

class InMemoDb(private val crypto: Crypto? = null) : IDb {
    private var users = mutableMapOf<String, User>()
    private val usernames = mutableMapOf<String, String>()
    private val passwords = mutableMapOf<String, String>()
    private val accounts = mutableMapOf<String, String>()


    override fun boot() {
        if (File(DB_FILENAME).exists()) {
            users = JSON.parse(File(DB_FILENAME).readText()) ?: mutableMapOf()
            val persistedPasswords = loadPassword()
            passwords.clear()
            usernames.clear()
            for ((id, u) in users) {
                usernames[u.username] = id
                passwords[id] = persistedPasswords?.getOrDefault(id, "password") ?: "password"
                indexAccount(u)
            }

            return
        }

        // Defaults
        defaultUsers()
    }

    private fun indexAccount(user: User) {
        for (account in user.accounts) {
            accounts[account.id] = user.id
        }
    }

    private fun loadPassword(): MutableMap<String, String>? {
        if (!File(DB_PWD_FILENAME).exists()) {
            return null
        }

        val encryptedPasswords = JSON.parse<MutableMap<String, String>>(File(DB_PWD_FILENAME).readText())
        val passwords = mutableMapOf<String, String>()
        if (crypto == null) {
            return encryptedPasswords
        }

        for ((id, ePwd) in encryptedPasswords) {
            passwords[id] = crypto.decrypt(ePwd)
        }

        return passwords
    }

    private fun defaultUsers() {
        val admin = UserFactory.makeAdmin("admin")
        addUser(admin)
        resetUserPassword(admin.id, "password")

        val testUserNoWallet = UserFactory.makeUser("mario nowallet")
        val testUserWithWallet = UserFactory.makeUser("mario wallet", Money(100, Currency.EURO))
        addUser(testUserNoWallet)
        resetUserPassword(testUserNoWallet.id, "mario")
        addUser(testUserWithWallet)
        indexAccount(testUserWithWallet)
        resetUserPassword(testUserWithWallet.id, "mario")
    }

    override fun cleanup() {
        persist()
        // maybe close connections and stuff
    }

    private fun persist() {
        //save to file
        val encryptedPasswords = mutableMapOf<String, String>()

        if (crypto != null) {
            for ((id, pwd) in passwords) {
                encryptedPasswords[id] = crypto.encrypt(pwd)
            }
        }

        File(DB_FILENAME).writeText(JSON.stringify(users))
        File(DB_PWD_FILENAME).writeText(JSON.stringify(encryptedPasswords))
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

    override fun tryUpdateUserAccount(userId: String, amount: Money): Boolean {
        val user = getUserById(userId) ?: return false

        try {
            val selectedAccount = user.selectedAccount ?: AccountFactory.makeEmpty(amount.currency)
            val updatedAccount = selectedAccount.copy(balance = selectedAccount.balance + amount)
            updateUserAccounts(updatedAccount, user)
            users[userId]?.selectedAccount = updatedAccount
        } catch (e: Exception) {
            return false
        }

        return true
    }

    private fun updateUserAccounts(
        updatedAccount: Account,
        user: User,
    ) {
        val newAccounts = user.accounts.filter { it.id != updatedAccount.id }.toMutableList()
        newAccounts.add(updatedAccount)

        val newUser = user.copy(accounts = newAccounts)
        updateUser(newUser)
    }

    override fun tryUpdateAccountById(accountId: String, amount: Money): Account? {
        val (account, _) = getAccountById(accountId)
        if (account == null) return null

        return try {
            account.copy(balance = account.balance + amount)
        } catch (_: Exception) {
            null
        }
    }

    override fun getAccountById(accountId: String): Pair<Account?, String?> {
        val userId = accounts[accountId] ?: return Pair(null, null)

        val user = getUserById(userId) ?: return Pair(null, null)

        return Pair(user.accounts.find { it.id == accountId }, userId)
    }

    override fun transferFunds(fromId: String, toId: String, amount: Money): Boolean {
        val (_, fromUserId) = getAccountById(fromId)
        val (_, toUserId) = getAccountById(toId)

        val newFrom = tryUpdateAccountById(fromId, amount * -1) ?: return false
        val newTo = tryUpdateAccountById(toId, amount) ?: return false

        updateUserAccounts(newFrom, users[fromUserId]!!)
        updateUserAccounts(newTo, users[toUserId]!!)
        
        users[fromUserId]?.selectedAccount = newFrom

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