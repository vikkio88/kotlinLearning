package org.vikkio

import io.github.cdimascio.dotenv.dotenv
import org.vikkio.app.App
import org.vikkio.data.jdbcSqliteDb.DbHelper
import org.vikkio.models.User

import org.vikkio.models.UserFactory
import org.vikkio.models.enums.UserType
import java.sql.DriverManager


fun main() {
//    val env = dotenv()
//    val appSecret = env["APP_SECRET"] ?: "secret"
//    val app = App(appSecret)
//    app.run()


    println(DbHelper.migrateIfNotExists(DriverManager.getConnection("jdbc:sqlite:test2.db"), User::class))
    return
    /*
    val db = JdbcSqliteDb()Î


        val u = db.getUserById("01J0KA9CY9QE6EQTBMHJ4S8DWD") ?: return
        println(u)
        println(u.role)
        println()
        val u2 = u.copy(role = UserType.ADMIN)
        db.users.update(u2, u2.id)
        val u3 = db.getUserById("01J0KA9CY9QE6EQTBMHJ4S8DWD") ?: return
        println(u3)
        println(u3.role)
        println()

        if (db.addUser(UserFactory.makeUser("Mario Mario"))) {
            println("Added Mario")
        } else {
            println("Couldnt add mario")
        }
        val us = db.getUsers()
        for (u1 in us) {
            println(u1)
        }


    //    val l = UserFactory.makeUser("Facci Di Minchia")
    //    db.addUser(l)
    */

}