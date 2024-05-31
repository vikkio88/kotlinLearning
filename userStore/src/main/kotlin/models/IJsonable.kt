package org.vikkio.models

import kotlinx.serialization.*
import kotlinx.serialization.json.Json

interface IJsonable {
    fun toJson():String {
        return Json.encodeToString(this)
    }
}