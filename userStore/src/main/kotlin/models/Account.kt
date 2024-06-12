package org.vikkio.models

import com.github.f4b6a3.ulid.UlidCreator
import kotlinx.serialization.*
import org.vikkio.models.enums.Currency

const val ACCOUNT_PREFIX = "ba-"

@Serializable
data class Account(val id: String, val balance: Money, var name: String? = null) {
    val currency: Currency
        get() = balance.currency

    override fun toString(): String {
        return "${name ?: id} : $balance"
    }

    fun toString(full: Boolean): String {
        return "${if (name != null) "name: $name\n" else ""}accountId: $id\nbalance: $balance\n"
    }
}

object AccountFactory {
    fun makeAccount(amount: Money): Account {
        return Account("$ACCOUNT_PREFIX${UlidCreator.getUlid()}", amount)
    }

    fun makeEmpty(currency: Currency): Account {
        return Account("temp", Money(0, currency))
    }
}