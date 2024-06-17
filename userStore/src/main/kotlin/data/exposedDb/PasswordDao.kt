package org.vikkio.data.db

import com.github.f4b6a3.ulid.UlidCreator
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column
import org.vikkio.libs.Crypto

object Passwords : IdTable<String>() {
    override val id: Column<EntityID<String>> = Passwords.varchar("id", 50).entityId()
    val passwordHash = varchar("passwordHash", 255)

    // Reference to Users table
    val user = reference("user", Users)
}


class PasswordDao(id: EntityID<String>) : Entity<String>(id) {
    companion object : EntityClass<String, PasswordDao>(Passwords) {
        fun new(user: UserDao, password: String, crypto: Crypto? = null): PasswordDao {
            if (crypto !== null) {
                return PasswordDao.new(UlidCreator.getUlid().toString()) {
                    passwordHash = crypto.encrypt(password)
                    this.user = user
                }
            }

            return PasswordDao.new(UlidCreator.getUlid().toString()) {
                this.user = user
                passwordHash = password
            }
        }
    }

    var passwordHash by Passwords.passwordHash
    var user by UserDao referencedOn Passwords.user

    fun checkPassword(clearText: String, crypto: Crypto? = null): Boolean {
        if (crypto == null) {
            return clearText == this.passwordHash
        }

        return crypto.decrypt(passwordHash) == clearText
    }
}
