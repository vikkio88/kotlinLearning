package org.vikkio.app.methods

import org.vikkio.app.AppState

val adminMenuMethodMap = mapOf(
    arrayOf("a", "add") to Methods.ADD_USER,
    arrayOf("l", "ls", "list") to Methods.LIST_USERS,
    arrayOf("acp") to Methods.ADMIN_CHANGE_PASSWORD,
    arrayOf("lo", "logout") to Methods.LOGOUT,
    arrayOf("q", "quit", "exit") to Methods.QUIT,
)

val userMenuMethodMap = mapOf(
    arrayOf("w","withdraw") to Methods.WITHDRAW,
    arrayOf("d","deposit") to Methods.DEPOSIT,
    arrayOf("lo", "logout") to Methods.LOGOUT,
    arrayOf("q", "quit", "exit") to Methods.QUIT,
)

val loginMenuMap = mapOf(
    arrayOf("l", "login") to Methods.LOGIN,
    arrayOf("q", "quit", "exit") to Methods.QUIT,
)

val stateToMethods = mapOf(
    AppState.AdminLoggedIn to adminMenuMethodMap,
    AppState.UserLoggedIn to userMenuMethodMap,
    AppState.LoggedOut to loginMenuMap,
)
