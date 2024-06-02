package org.vikkio.models

import kotlinx.serialization.Serializable
import ulid.ULID

@Serializable
data class User(val fullName: String, val wallet: Money) : Jsonable(){
    val id: String = ULID.nextULID().toString()
}