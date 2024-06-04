package org.vikkio.app

import org.vikkio.data.IDb

class Context(val db: IDb) {
    private var state: AppState = AppState.LoggedOut

    fun changeState(newState: AppState) {
        state = newState
    }

    fun getState(): AppState {
        return state
    }

}