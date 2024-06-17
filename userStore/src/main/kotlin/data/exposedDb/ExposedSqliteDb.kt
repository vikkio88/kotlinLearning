package org.vikkio.data.exposedDb

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.transactions.transaction
import org.vikkio.data.IDb
import org.vikkio.data.db.*
import org.vikkio.libs.Crypto
import org.vikkio.libs.JSON
import org.vikkio.models.Account
import org.vikkio.models.Money
import org.vikkio.models.User
import org.vikkio.org.vikkio.models.Transaction

class ExposedSqliteDb(private val crypto: Crypto?) : IDb {

    override fun boot() {
        Database.connect("jdbc:sqlite:test.db", driver = "org.sqlite.JDBC")
        transaction {
            SchemaUtils.create(
                Accounts,
                Users,
                Passwords,
            )
        }
    }

    override fun cleanup() {
    }

    override fun addUser(user: User): Boolean {
        transaction {
            UserDao.new(user)
        }
        return true
    }

    override fun deleteUser(userId: String): Boolean {
        transaction {
            UserDao.findById(userId)?.delete()
        }
        return true
    }

    override fun getUsers(): Iterable<User> {
        return transaction {
            return@transaction UserDao.all().map { it.toUser() }
        }
    }

    override fun getUserById(id: String): User? {
        return transaction {
            return@transaction UserDao.findById(id)?.toUser()
        }
    }

    override fun tryUpdateUserAccount(userId: String, amount: Money, accountId: String?): Boolean {
        return transaction {
            val userDao = UserDao.findById(userId) ?: return@transaction false
            val a = AccountDao.find { Accounts.user eq userDao.id and (Accounts.id eq accountId) }.singleOrNull()
                ?: return@transaction false
            try {
                val newB = a.toAccount().balance + amount
                AccountDao.findByIdAndUpdate(
                    a.id.toString()
                ) {
                    it.jsonBalance = JSON.stringify(newB)
                }
            } catch (_: Exception) {
                return@transaction false
            }

            true
        }
    }

    override fun tryUpdateAccountById(accountId: String, amount: Money): Account? {
        return transaction {
            var retAcc: Account?
            try {
                retAcc = AccountDao.findByIdAndUpdate(
                    accountId
                ) {
                    val newB = it.toAccount().balance + amount
                    it.jsonBalance = JSON.stringify(newB)
                }?.toAccount()
            } catch (_: Exception) {
                return@transaction null
            }

            retAcc
        }
    }

    override fun getAccountById(accountId: String): Pair<Account?, String?> {
        return transaction {

            val accDao = AccountDao.findById(accountId)

            return@transaction accDao?.toAccount() to accDao?.user?.id?.toString()
        }
    }

    override fun transferFunds(fromId: String, toId: String, amount: Money): Boolean {
        return transaction {

            true
        }
    }

    override fun resetUserPassword(userId: String, newPassword: String): Boolean {
        return transaction {
            val userDao = UserDao.findById(userId) ?: return@transaction false

            PasswordDao.new(userDao, password = crypto?.encrypt(newPassword) ?: newPassword)

            true
        }

    }

    override fun setUserPassword(userId: String, newPassword: String, oldPassword: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun tryLogin(username: String, password: String): User? {
        var user: User? = null
        transaction {
            val userDao = UserDao.find { Users.username eq username }.singleOrNull()
            userDao?.let { userEntity ->
                // Find the password entry for the user
                val passwordDao = PasswordDao.find { Passwords.user eq userEntity.id }.singleOrNull()

                if (passwordDao?.checkPassword(password, crypto) == true) {
                    user = userDao.toUser()
                }
            }
        }

        return user
    }

    override fun updateUser(user: User): Boolean {
        return transaction {
            UserDao.findByIdAndUpdate(user.id) {

                it.username = user.username
                it.fullName = user.fullName
            } ?: return@transaction false

            true
        }
    }

    override fun storeTransaction(transaction: Transaction) {
        TODO("Not yet implemented")
    }

    override fun getTransactions(): Iterable<Transaction> {
        TODO("Not yet implemented")
    }
}