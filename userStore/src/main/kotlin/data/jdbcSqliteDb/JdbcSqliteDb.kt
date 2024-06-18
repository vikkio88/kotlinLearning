package org.vikkio.data.jdbcSqliteDb

import org.vikkio.models.User
import java.sql.Connection
import java.sql.DriverManager

class JdbcSqliteDb(private val connection: Connection = DriverManager.getConnection("jdbc:sqlite:test.db")) {
    val users = UserDao(connection)
    fun boot() {

    }

    fun cleanup() {
        connection.close()
    }

    fun getUserById(id: String): User? {
        return users.findOne(id)
    }

    fun getUsers(): Iterable<User> {
        return users.all()
    }

    fun addUser(user: User): Boolean {
        return users.create(user)
//        val stm = pstm("insert into Users(id, fullName, username, role) values(?, ?, ?, ?)")
//        stm.setString(1, user.id)
//        stm.setString(2, user.fullName)
//        stm.setString(3, user.username)
//        stm.setString(4, user.role.toString())
//        return stm.executeUpdate() > 0
    }
}