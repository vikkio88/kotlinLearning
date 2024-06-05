package org.vikkio.app.methods

import org.vikkio.app.AppState
import org.vikkio.app.Context
import org.vikkio.cli.input

val login = { ctx: Context ->
    val username = input("Username: ") ?: ""
    val password = input("Password: ") ?: ""
    val user = ctx.db.tryLogin(username, password)
    if (user != null
    ) {
        println("Login successful...")
        ctx.login(user)
    } else {
        println("Invalid login, try again.")
    }
}

val logout = { ctx: Context ->
    println("Logging you out.")
    ctx.changeState(AppState.LoggedOut)
}