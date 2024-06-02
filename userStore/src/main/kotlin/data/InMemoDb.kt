package org.vikkio.data

import org.vikkio.models.User

class InMemoDb : IDb {
    private val users = mutableListOf<User>()
    override fun addUser(user: User) {
        users.add(user)
    }

    override fun getUsers(): Iterable<User> {
        return users
    }
}