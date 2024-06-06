package org.vikkio.app.methods

import org.vikkio.app.AppState

val methodMap = mapOf(
    Methods.LOGIN to login,
    Methods.LOGOUT to logout,

    Methods.ADD_USER to addUser,
    Methods.LIST_USERS to listUsers,
    Methods.ADMIN_CHANGE_PASSWORD to adminChangePassword,

    Methods.WITHDRAW to withdraw
)

val adminMenuMethodMap = mapOf(
    arrayOf("a", "add") to Methods.ADD_USER,
    arrayOf("l", "ls", "list") to Methods.LIST_USERS,
    arrayOf("acp") to Methods.ADMIN_CHANGE_PASSWORD,
    arrayOf("lo", "logout") to Methods.LOGOUT,
    arrayOf("q", "quit", "exit") to Methods.QUIT,
)

val userMenuMethodMap = mapOf(
    arrayOf("w","withdraw") to Methods.WITHDRAW,
    arrayOf("lo", "logout") to Methods.LOGOUT,
    arrayOf("q", "quit", "exit") to Methods.QUIT,
)

val loginMenuMap = mapOf(
    arrayOf("l", "login") to Methods.LOGIN,
    arrayOf("q", "quit", "exit") to Methods.QUIT,
)

val methodLabels = mapOf(
    Methods.LOGIN to "Login",
    Methods.LOGOUT to "Logout",

    Methods.ADD_USER to "Add a user",
    Methods.LIST_USERS to "List all users",
    Methods.ADMIN_CHANGE_PASSWORD to "Change password",

    Methods.WITHDRAW to "Withdraw",

    Methods.QUIT to "Quit app",
)

val stateToMethods = mapOf(
    AppState.AdminLoggedIn to adminMenuMethodMap,
    AppState.UserLoggedIn to userMenuMethodMap,
    AppState.LoggedOut to loginMenuMap,
)
