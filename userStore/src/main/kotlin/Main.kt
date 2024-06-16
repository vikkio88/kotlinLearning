package org.vikkio

import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.vikkio.models.User
import org.vikkio.models.enums.UserType
import java.sql.DriverManager
import java.sql.ResultSet

object Users : IntIdTable() {
    val fullName = varchar("fullName", 50)
    val role = enumeration<UserType>("role")
    val username = varchar("username", 50)
}

fun main() {
//    val env = dotenv()
//    val appSecret = env["APP_SECRET"] ?: "secret"
//    val app = App(appSecret)
//    app.run()
    Database.connect("jdbc:sqlite:test.db", driver = "org.sqlite.JDBC")
    transaction {

//        addLogger(StdOutSqlLogger)

        SchemaUtils.create(Users)


        Users.insert {
            it[fullName] = "Mario Giacomelli"
            it[role] = UserType.USER
            it[username] = "mario.giacomelli"
        }

        val users = Users.selectAll()

        for (u in users) {
            println("${u[Users.id]} - ${u[Users.username]}")
        }
    }

}