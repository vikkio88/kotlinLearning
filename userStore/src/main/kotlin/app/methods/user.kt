package org.vikkio.app.methods

import org.vikkio.cli.input
import org.vikkio.cli.inputNumber
import org.vikkio.models.Currency
import org.vikkio.models.Money
import org.vikkio.models.User

val addUser = { users: MutableCollection<User> ->
    val name = input("Full name: ") ?: "Unknown"
    val money = inputNumber("Balance in $: ") ?: 0

    val user = User(name, Money(money, Currency.US_DOLLAR))
    users.add(user)
}

val listUsers = { users: MutableCollection<User> ->
    users.forEach {
        println(it.fullName)
    }
}
