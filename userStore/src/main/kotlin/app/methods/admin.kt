package org.vikkio.app.methods

import org.vikkio.app.Context
import org.vikkio.cli.getFromList
import org.vikkio.cli.input
import org.vikkio.cli.inputNumber
import org.vikkio.models.Money
import org.vikkio.models.User
import org.vikkio.models.enums.Currency
import kotlin.random.Random

const val MAX_RETRY: Int = 3

val addUser = { ctx: Context ->
    val name = input("Full name: ") ?: "Unknown"
    val money = inputNumber("Balance in $: ")

    var user = User(name, Money(money, Currency.US_DOLLAR))
    var added = ctx.db.addUser(user)
    var tries = 0
    while (!added && tries < MAX_RETRY) {
        val newUsername = user.username + Random.nextInt(1000, 9999)
        println("error: Could not add user with username:'${user.username}', generating a new one.")
        user = user.copy(username = newUsername)
        added = ctx.db.addUser(user)
        tries += 1
    }


    if (added) {
        println("User '${user.username}' generated")
    }
}

val listUsers = { ctx: Context ->
    ctx.db.getUsers().forEach {
        println(it.fullName)
    }
}


val adminChangePassword = { ctx: Context ->
    val (index, user) = getFromList(ctx.db.getUsers().toList())
    if (index < 0) {
        println("No user selected.")

    } else if (user is User) {
        println("selected user [${index + 1}]: ${user.fullName}")
        val password = input("New Password > ")

        if (password?.isEmpty() == true) println("No password specified, using default 'PASSWORD'.")
        val res = ctx.db.resetUserPassword(user.id, password ?: "PASSWORD")
        println(if (res) "Password changed correctly." else "Failed to change Password.")
    }
}