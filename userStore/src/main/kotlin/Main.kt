package org.vikkio

import io.github.cdimascio.dotenv.dotenv
import org.vikkio.app.App
import org.vikkio.data.jdbcsqlite.JdbcSqliteDb
import org.vikkio.data.normDb.NormDb
import org.vikkio.models.UserFactory


fun main() {
//    val env = dotenv()
//    val appSecret = env["APP_SECRET"] ?: "secret"
//    val app = App(appSecret)
//    app.run()
    val db = NormDb()
    db.boot()


//    val l = UserFactory.makeUser("Facci Diminchiax")
//    db.addUser(l)

    for (u in db.getUsers()) {
        println(u)
    }


}