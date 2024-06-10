package org.vikkio.app

import org.vikkio.data.IDb
import org.vikkio.models.User
import org.vikkio.models.enums.UserType

class Context(val db: IDb, private var loggedInUser: User? = null) {
    var state: AppState = AppState.LoggedOut
        private set

    fun login(user: User) {
        when (user.role) {
            UserType.USER -> {
                changeState(AppState.UserLoggedIn)
            }

            UserType.ADMIN -> {
                changeState(AppState.AdminLoggedIn)
            }
        }
        updateUser(user)
    }

    fun updateUser(user: User?) {
        loggedInUser = user
    }

    fun changeState(newState: AppState) {
        state = newState
    }

    fun getLoggedInUser(): User? {
        return loggedInUser

    }

    fun persistChanges() {
        if (loggedInUser != null) {
            db.updateUser(loggedInUser!!)
        }
    }

}