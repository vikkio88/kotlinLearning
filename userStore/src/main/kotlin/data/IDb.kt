package org.vikkio.data

import org.vikkio.models.Money
import org.vikkio.models.User

interface IDb {
    fun boot()
    fun cleanup()

    fun addUser(user: User): Boolean
    fun deleteUser(userId: String): Boolean
    fun getUsers(): Iterable<User>
    fun getUserById(id: String): User?
    fun tryUpdateWallet(userId: String, amount: Money, accountId: String? = null): Boolean

    fun resetUserPassword(userId: String, newPassword: String): Boolean
    fun setUserPassword(userId: String, newPassword: String, oldPassword: String): Boolean
    fun tryLogin(username: String, password: String): User?
    fun updateUser(user: User): Boolean
}