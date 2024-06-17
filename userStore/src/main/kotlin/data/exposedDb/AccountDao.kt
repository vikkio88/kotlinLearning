package org.vikkio.data.db

import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column
import org.vikkio.libs.JSON
import org.vikkio.models.Account

object Accounts : IdTable<String>() {
    override val id: Column<EntityID<String>> = varchar("id", 50).entityId()
    val user = reference("users", Users)
    val jsonBalance = varchar("jsonBalance", 255)
    val name = varchar("name", 255).nullable()


}

class AccountDao(id: EntityID<String>) : Entity<String>(id) {
    companion object : EntityClass<String, AccountDao>(Accounts) {
        fun new(account: Account, user: UserDao): AccountDao {
            return new(account.id) {
                this.user = user
                name = account.name
                jsonBalance = JSON.stringify(account.balance)
            }

        }
    }

    var name by Accounts.name
    var jsonBalance by Accounts.jsonBalance
    var user by UserDao referencedOn Accounts.user

    fun toAccount(): Account {
        return Account(id.toString(), JSON.parse(jsonBalance), name)
    }

}
