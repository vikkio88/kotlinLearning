package org.vikkio.app.methods

import org.vikkio.app.Context

enum class Methods(val label: String, val body: ((Context) -> Unit)? = null) {
    LOGIN("Login", login),
    LOGOUT("Logout", logout),

    //Admin methods
    ADD_USER("Add a user", addUser),
    LIST_USERS("List all users", listUsers),
    ADMIN_CHANGE_PASSWORD("Reset User Password", adminChangePassword),
    DELETE_USER("Delete User", deleteUser),

    // Users
    CHANGE_PASSWORD("Change Password", changePassword),

    // -> Money movements
    WITHDRAW("Withdraw", withdraw),
    DEPOSIT("Deposit", deposit),
    MOVE_MONEY("Move"),

    // -> Account Utilities
    ACCOUNT_INFO("Account Info", accountInfo),
    SELECT_ACCOUNT("Select Account", selectAccount),
    CREATE_NEWACCOUNT("Create New Account", createNewAccount),
    RENAME_ACCOUNT("Rename Account", renameAccount),


    QUIT("Quit app"),

    NO_METHOD(""),
}