package org.vikkio.models

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.ClassDiscriminatorMode
import kotlinx.serialization.json.Json

// This is to take out the "type"
val format = Json { classDiscriminatorMode = ClassDiscriminatorMode.NONE }
//val format = Json

object JSON {
    inline fun <reified T> stringify(o: T): String {
        return format.encodeToString(o)
    }

    inline fun <reified self> parse(str: String): self {
        return Json.decodeFromString<self>(str)
    }
}