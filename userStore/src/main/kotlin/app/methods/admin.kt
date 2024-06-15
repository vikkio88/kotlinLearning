package org.vikkio.app.methods

import org.vikkio.app.Context
import org.vikkio.cli.getFromList
import org.vikkio.cli.hiddenInput
import org.vikkio.cli.input
import org.vikkio.cli.yNInput
import org.vikkio.models.UserFactory
import kotlin.random.Random

const val MAX_RETRY: Int = 3

val addUser = fun(ctx: Context) {
    val name = input("Full name: ") ?: "Unknown"
    val mainAccount = createAccount()
    var user = UserFactory.makeUser(name, mainAccount)
    var added = ctx.db.addUser(user)
    var tries = 0
    while (!added && tries < MAX_RETRY) {
        val newUsername = user.username + Random.nextInt(1000, 9999)
        println("error: Could not add user with username:'${user.username}', generating a new one.")
        user = user.copy(username = newUsername)
        added = ctx.db.addUser(user)
        tries += 1
    }

    if (!added) {
        println("Could not create the user. Try again.")
        return
    }

    println("User '${user.username}' generated")
}

val listUsers = { ctx: Context ->
    ctx.db.getUsers().forEachIndexed { i, u ->
        println("${i + 1} - $u")
    }
}

val deleteUser = fun(ctx: Context) {
    val (index, user) = getFromList(ctx.db.getUsers().toList())
    if (index < 0) {
        println("No user selected.")
        return

    }
    println("selected user [${index + 1}]: ${user!!.fullName}")
    if (!yNInput("Confirm deletion?")) {
        println("Cancelling deletion, to confirm type 'yes'.")
        return
    }

    println("Deletion confirmed.")
    println("Deleting user '${user.username}'.")
    ctx.db.deleteUser(user.id)
    println("user deleted.")
}


val adminChangePassword = fun(ctx: Context) {
    val (index, user) = getFromList(ctx.db.getUsers().toList())
    if (index < 0) {
        println("No user selected.")
        return

    }
    println("selected user [${index + 1}]: ${user!!.fullName}")
    val password = hiddenInput("New password: ")

    if (password?.isEmpty() == true) println("No password specified, using default 'PASSWORD'.")
    val res = ctx.db.resetUserPassword(user.id, password ?: "PASSWORD")
    println(if (res) "Password changed correctly." else "Failed to change Password.")
}

val listTransaction = fun(ctx: Context) {
    println("Listing all transactions:")
    for (trn in ctx.db.getTransactions()) {
        println(trn)
    }
}