package org.vikkio.app.methods

import org.vikkio.cli.getFromList
import org.vikkio.cli.input
import org.vikkio.cli.inputNumber
import org.vikkio.models.Currency
import org.vikkio.models.Money
import org.vikkio.models.User
import org.vikkio.data.IDb

val addUser = { db: IDb ->
    val name = input("Full name: ") ?: "Unknown"
    val money = inputNumber("Balance in $: ") ?: 0

    val user = User(name, Money(money, Currency.US_DOLLAR))
    println(user.toJson())
    db.addUser(user)
}

val listUsers = { db: IDb ->
    db.getUsers().forEach {
        println(it.fullName)
    }
}

val adminChangePassword = { db: IDb ->
    val user = getFromList(db.getUsers().toList())
    println("selected ${user?.toJson() ?: "NO USER"}")
}
