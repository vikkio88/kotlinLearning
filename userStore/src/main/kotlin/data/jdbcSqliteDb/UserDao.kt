package org.vikkio.data.jdbcSqliteDb


import org.vikkio.models.User
import org.vikkio.models.enums.UserType
import java.sql.Connection
import java.sql.ResultSet

class UserDao(connection: Connection) : Entity<User, String>(connection) {
    override val table: String = "Users"
    override val primaryKey: String = "id"


    override fun map(rs: ResultSet) = User(
        id = rs.getString("id"),
        username = rs.getString("username"),
        fullName = rs.getString("fullName"),
        role = UserType.byNameIgnoreCaseOrDefault(rs.getString("role"))
    )

    override fun map(obj: User): ColumnMap {
        return mapOf(
            "id" to { index, stm -> stm.setString(index, obj.id) },
            "username" to { index, stm -> stm.setString(index, obj.username) },
            "fullName" to { index, stm -> stm.setString(index, obj.fullName) },
            "role" to { index, stm -> stm.setString(index, obj.role.toString()) },
        )
    }

}




