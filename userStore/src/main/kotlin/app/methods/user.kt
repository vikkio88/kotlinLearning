package org.vikkio.app.methods

import org.vikkio.app.AppState
import org.vikkio.app.Context
import org.vikkio.cli.input
import org.vikkio.models.enums.UserType

val login = { ctx: Context ->
    val username = input("Username: ") ?: ""
    val password = input("Password: ") ?: ""
    val user = ctx.db.login(username, password)
    if (user != null
    ) {
        when (user.role) {
            UserType.USER -> ctx.changeState(AppState.UserLoggedIn)
            UserType.ADMIN -> ctx.changeState(AppState.AdminLoggedIn)
        }
    } else {
        println("Invalid login, try again.")
    }
}

val logout = { ctx: Context ->
    println("Logging you out.")
    ctx.changeState(AppState.LoggedOut)
}