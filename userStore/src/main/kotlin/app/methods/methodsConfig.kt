package org.vikkio.app.methods

import org.vikkio.app.AppState

val adminMenuMethodMap = mapOf(
    arrayOf("a", "add") to Methods.ADD_USER,
    arrayOf("l", "ls", "list") to Methods.LIST_USERS,
    arrayOf("rp") to Methods.ADMIN_CHANGE_PASSWORD,
    arrayOf("du") to Methods.DELETE_USER,
    arrayOf("cp", "change:password") to Methods.CHANGE_PASSWORD,
    arrayOf("lo", "logout") to Methods.LOGOUT,
    arrayOf("q", "quit", "exit") to Methods.QUIT,
)

val userMenuMethodMap = mapOf(
    arrayOf("cp", "change:password") to Methods.CHANGE_PASSWORD,
    arrayOf("w", "withdraw") to Methods.WITHDRAW,
    arrayOf("d", "deposit") to Methods.DEPOSIT,
    arrayOf("m", "move") to Methods.MOVE_MONEY,
    arrayOf("p", "pay") to Methods.PAY,

    arrayOf("ai", "account:info") to Methods.ACCOUNT_INFO,
    arrayOf("ra", "rename:account") to Methods.RENAME_ACCOUNT,
    arrayOf("sa", "select:account") to Methods.SELECT_ACCOUNT,
    arrayOf("cna", "create:account") to Methods.CREATE_NEWACCOUNT,
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
