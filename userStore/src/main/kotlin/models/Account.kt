package org.vikkio.models

import kotlinx.serialization.*
import org.vikkio.models.enums.Currency

@Serializable
data class Account(val id: String, val balance: Money, var name: String? = null) {
    val currency: Currency
        get() = balance.currency

    override fun toString(): String {
        return "${name ?: id} : $balance"
    }
}

object AccountFactory {
    fun makeAccount(amount: Money): Account {
        return Account("id",  amount)
    }

    fun makeEmpty(currency: Currency): Account {
        return Account("temp", Money(0, currency))
    }
}