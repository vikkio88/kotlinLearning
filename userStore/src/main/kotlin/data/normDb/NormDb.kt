package org.vikkio.data.normDb

import com.dieselpoint.norm.Database
import org.vikkio.models.User

class NormDb {
    private var db: Database? = null
    fun boot() {
        db = Database()
        db!!.setJdbcUrl("jdbc:sqlite:test.db")
    }

    fun cleanup() {
        db?.connection?.close()
    }


    fun getUsers(): Iterable<UserDao> {
        return db?.where("1 = 1")!!.results(UserDao::class.java)
    }

    fun addUser(user: User): Boolean {

        db?.insert(UserDao.from(user))

        return true
    }
}