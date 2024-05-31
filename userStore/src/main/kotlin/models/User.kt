package org.vikkio.models

import kotlinx.serialization.Serializable

@Serializable
data class User(val fullName: String, val wallet: Money) : IJsonable