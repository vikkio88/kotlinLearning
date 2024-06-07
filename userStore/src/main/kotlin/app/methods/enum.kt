package org.vikkio.app.methods

enum class Methods {
    LOGIN,
    LOGOUT,

    //Admin methods
    ADD_USER,
    LIST_USERS,
    ADMIN_CHANGE_PASSWORD,

    // Users
    WITHDRAW,
    DEPOSIT,
    MOVE,

    QUIT,

    NO_METHOD,
}