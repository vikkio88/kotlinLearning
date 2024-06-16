package org.vikkio

import java.sql.DriverManager
import java.sql.ResultSet


fun main() {
//    val env = dotenv()
//    val appSecret = env["APP_SECRET"] ?: "secret"
//    val app = App(appSecret)
//    app.run()
    val connection = DriverManager.getConnection("jdbc:sqlite:test.db")


    val stm = connection.prepareStatement(
        """
            CREATE TABLE IF NOT EXISTS users (  
                id VARCHAR(255) PRIMARY KEY,
                fullname VARCHAR(255) NOT NULL,
                role TEXT NOT NULL CHECK(role IN ('USER', 'ADMIN')),
                username VARCHAR(255) UNIQUE NOT NULL
            );
        """.trimIndent()
    )
    stm.execute()
    stm.close()

    val statement = connection.createStatement()
    val rs: ResultSet = statement.executeQuery("select * from users")
    while (rs.next()) {
        println("name = " + rs.getString("name"))
        println("id = " + rs.getInt("id"))
    }

}