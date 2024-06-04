package org.vikkio.models.enums

enum class UserType {
    USER,
    ADMIN;

    override fun toString(): String {
        return getName()
    }

    private fun getName() = name.lowercase()
}