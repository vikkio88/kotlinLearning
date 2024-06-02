package org.vikkio.data

import org.vikkio.models.User

interface IDb {
    fun addUser(user: User)
    fun getUsers(): Iterable<User>
}