package org.vikkio.app

import org.vikkio.data.IDb
import org.vikkio.models.User
import org.vikkio.models.enums.UserType

class Context(val db: IDb, private var loggedInUser: User? = null) {
    private var state: AppState = AppState.LoggedOut


    fun login(user: User) {
        when (user.role) {
            UserType.USER -> {
                changeState(AppState.UserLoggedIn)
            }

            UserType.ADMIN -> {
                changeState(AppState.AdminLoggedIn)
            }
        }
        loggedInUser = user
    }

    fun changeState(newState: AppState) {
        state = newState
    }

    fun getState(): AppState {
        return state
    }

    fun getLoggedInUser(): User? {
        return loggedInUser

    }

}