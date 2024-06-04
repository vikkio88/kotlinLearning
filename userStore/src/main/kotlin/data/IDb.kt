package org.vikkio.data

import org.vikkio.models.User

interface IDb {
    fun addUser(user: User): Boolean
    fun getUsers(): Iterable<User>
    fun getUserById(id: String): User?

    fun resetUserPassword(userId: String, newPassword: String): Boolean
    fun setUserPassword(userId: String, newPassword: String, oldPassword: String): Boolean
    fun login(username: String, password: String): User?
}