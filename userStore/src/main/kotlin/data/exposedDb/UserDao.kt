package org.vikkio.data.db

import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.transactions.transaction
import org.vikkio.models.User
import org.vikkio.models.enums.UserType

object Users : IdTable<String>() {
    override val id: Column<EntityID<String>> = varchar("id", 50).entityId()
    val fullName = varchar("fullName", 50)
    val role = enumeration<UserType>("role")
    val username = varchar("username", 50).uniqueIndex()
}

class UserDao(id: EntityID<String>) : Entity<String>(id) {
    companion object : EntityClass<String, UserDao>(Users) {
        fun new(user: User): UserDao {
            return transaction {
                val userDao = UserDao.new(user.id) {
                    fullName = user.fullName
                    username = user.username
                    role = user.role
                }

                user.accounts.forEach { account ->
                    AccountDao.new(account, userDao)
                }

                userDao
            }
        }
    }

    var fullName by Users.fullName
    var username by Users.username
    var role by Users.role

    val accounts by AccountDao referrersOn Accounts.user


    fun toUser(): User {
        return User(
            id = id.value,
            fullName = fullName,
            username = username,
            role = role,
            accounts = accounts.map { it.toAccount() }.toMutableList()
        )
    }
}