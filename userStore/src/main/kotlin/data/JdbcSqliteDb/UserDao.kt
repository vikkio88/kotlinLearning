package org.vikkio.data.JdbcSqliteDb


import org.vikkio.models.User
import java.sql.Connection
import java.sql.ResultSet

class UserDao(connection: Connection) : Entity<User, String>(connection) {
    override val table: String = "Users"
    override val primaryKey: String = "id"


    override fun map(rs: ResultSet) = User(
        id = rs.getString("id"),
        username = rs.getString("username"),
        fullName = rs.getString("fullName")
    )

}


abstract class Entity<T, Id>(private val connection: Connection) {
    abstract val table: String
    abstract val primaryKey: String

    fun select(subset: String = "*"): String {
        return "select * from $table"
    }

    fun findOne(id: Id): T {
        val stm = connection.prepareStatement("${select()} where $primaryKey = ?")
        when (id) {
            is String -> stm.setString(1, id)
            is Int -> stm.setInt(1, id)
            else -> throw Exception()
        }

        return map(stm.executeQuery())
    }

    fun all(): Iterable<T> {
        val stm = connection.createStatement()
        val result = mutableListOf<T>()

        val rs = stm.executeQuery(select())
        while (rs.next()) {
            result.add(map(rs))
        }

        return result
    }

    abstract fun map(rs: ResultSet): T


}