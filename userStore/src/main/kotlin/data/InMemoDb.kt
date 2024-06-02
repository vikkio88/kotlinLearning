package org.vikkio.data

import org.vikkio.models.User

class InMemoDb : IDb {
    private val users = mutableMapOf<String, User>()
    private val passwords = mutableMapOf<String, String>()

    override fun addUser(user: User) {
        users[user.id] = user
    }

    override fun getUsers(): Iterable<User> {
        return users.values
    }

    override fun getUserById(id: String): User? {
        return users[id]
    }

    override fun setUserPassword(userId: String, newPassword: String, oldPassword: String?): Boolean {
        val oldPwd = passwords[userId]
        if (oldPwd != null && oldPwd != oldPassword) {
            return false
        }

        passwords[userId] = newPassword

        return true
    }

    override fun login(userId: String, password: String): Boolean {
        val pwd = passwords[userId]
        return pwd == password
    }
}