package org.vikkio.models

import kotlinx.serialization.*
import org.vikkio.models.enums.Currency

@Serializable
data class Account(val id: String, val currency: Currency, val balance: Money)

object AccountFactory {
    fun makeAccount(amount: Money): Account {
        return Account("id", amount.currency, amount)
    }

    fun makeEmpty(currency: Currency): Account {
        return Account("temp", currency, Money(0, currency))
    }
}