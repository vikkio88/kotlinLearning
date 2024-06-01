package org.vikkio.models

import kotlinx.serialization.*
import kotlinx.serialization.json.*

// This is to take out the "type"
val format = Json { classDiscriminatorMode = ClassDiscriminatorMode.NONE }


@Serializable
sealed class Jsonable {
    fun toJson(): String {
        return format.encodeToString(this)
    }

    inline fun <reified T> fromJson(str: String): T {
        return Json.decodeFromString<T>(str)
    }
}