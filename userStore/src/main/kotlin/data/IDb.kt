package org.vikkio.data

import org.vikkio.models.Account
import org.vikkio.models.Money
import org.vikkio.models.User
import org.vikkio.org.vikkio.models.Transaction

interface IDb {
    fun boot()
    fun cleanup()

    fun addUser(user: User): Boolean
    fun deleteUser(userId: String): Boolean
    fun getUsers(): Iterable<User>
    fun getUserById(id: String): User?
    fun tryUpdateUserAccount(userId: String, amount: Money): Boolean
    fun tryUpdateAccountById(accountId: String, amount: Money): Account?

    fun getAccountById(accountId: String): Pair<Account?, String?>
    fun transferFunds(fromId: String, toId: String, amount: Money): Boolean

    fun resetUserPassword(userId: String, newPassword: String): Boolean
    fun setUserPassword(userId: String, newPassword: String, oldPassword: String): Boolean
    fun tryLogin(username: String, password: String): User?
    fun updateUser(user: User): Boolean

    //TODO: transactions history
    fun getTransactions():Iterable<Transaction>
}