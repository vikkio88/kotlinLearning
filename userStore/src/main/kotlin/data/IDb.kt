package org.vikkio.data

import org.vikkio.models.User

interface IDb {
    fun addUser(user: User)
    fun getUsers(): Iterable<User>
    fun getUserById(id: String): User?

    fun setUserPassword(userId: String, newPassword: String, oldPassword: String? = null): Boolean
    fun login(userId: String, password: String): Boolean
}