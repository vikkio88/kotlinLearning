package org.vikkio.data.jdbcsqlite

import org.vikkio.data.JdbcSqliteDb.UserDao
import org.vikkio.models.User
import java.sql.Connection
import java.sql.DriverManager
import java.sql.PreparedStatement
import java.sql.Statement

class JdbcSqliteDb(private val connection: Connection = DriverManager.getConnection("jdbc:sqlite:test.db")) {
    fun boot() {

    }

    fun cleanup() {
        connection.close()
    }

    fun getUsers(): Iterable<User> {
        val u = UserDao(connection)

        return u.all()
    }

    fun addUser(user: User): Boolean {
        return false
//        val stm = pstm("insert into Users(id, fullName, username, role) values(?, ?, ?, ?)")
//        stm.setString(1, user.id)
//        stm.setString(2, user.fullName)
//        stm.setString(3, user.username)
//        stm.setString(4, user.role.toString())
//        return stm.executeUpdate() > 0
    }
}