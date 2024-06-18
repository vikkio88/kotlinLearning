package org.vikkio.models.enums

enum class UserType {
    USER,
    ADMIN;

    override fun toString(): String {
        return getName()
    }

    private fun getName() = name.lowercase()

    companion object {
        fun byNameIgnoreCaseOrDefault(input: String, default: UserType = USER): UserType {
            return entries.firstOrNull { it.name.equals(input, true) } ?: default
        }
    }
}