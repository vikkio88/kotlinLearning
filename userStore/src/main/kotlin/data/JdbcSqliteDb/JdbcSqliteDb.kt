package org.vikkio.data.jdbcsqlite

import org.vikkio.models.User
import java.sql.Connection
import java.sql.DriverManager
import java.sql.PreparedStatement
import java.sql.Statement

class JdbcSqliteDb {
    private var connection: Connection? = null
    fun boot() {
        connection = DriverManager.getConnection("jdbc:sqlite:test.db")
    }

    fun cleanup() {
        connection?.close()
    }

    private fun stm(): Statement = connection?.createStatement() ?: throw Exception()
    private fun pstm(query: String): PreparedStatement = connection?.prepareStatement(query) ?: throw Exception()

    fun getUsers(): Iterable<User> {
        val result = mutableListOf<User>()
        val rs = stm().executeQuery("select * from Users")
        while (rs.next()) {
            val u = User(
                id = rs.getString("id"),
                username = rs.getString("username"),
                fullName = rs.getString("fullName")
            )
            result.add(u)
        }
        return result
    }

    fun addUser(user: User): Boolean {
        val stm = pstm("insert into Users(id, fullName, username, role) values(?, ?, ?, ?)")
        stm.setString(1, user.id)
        stm.setString(2, user.fullName)
        stm.setString(3, user.username)
        stm.setString(4, user.role.toString())
        return stm.executeUpdate() > 0
    }
}