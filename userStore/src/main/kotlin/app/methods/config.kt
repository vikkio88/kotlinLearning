package org.vikkio.app.methods

import org.vikkio.data.IDb

val methodMap = mapOf<Methods, (IDb) -> Unit>(
    Methods.ADD_USER to addUser,
    Methods.LIST_USERS to listUsers
)

val menuMethodMap = mapOf(
    arrayOf("a", "add") to Methods.ADD_USER,
    arrayOf("l", "ls", "list") to Methods.LIST_USERS,
    arrayOf("q", "quit", "exit") to Methods.QUIT,
)

val methodLabels = mapOf(
    Methods.ADD_USER to "Add a user",
    Methods.LIST_USERS to "List all users",
    Methods.QUIT to "Quit app"
)