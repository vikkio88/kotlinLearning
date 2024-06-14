package org.vikkio.org.vikkio.models

import kotlinx.serialization.Serializable
import org.vikkio.models.Money
import org.vikkio.org.vikkio.libs.DateSerializer
import java.time.Instant
import java.util.*


@Serializable
data class Transaction(
    @Serializable(with = DateSerializer::class)
    val date: Date,
    val accountFromId: String,
    val accountToId: String,
    val amount: Money? = null,
    val userId: String? = null,
)


object TransactionFactory {
    fun make(accountFromId: String, accountToId: String, amount: Money? = null): Transaction {
        return Transaction(Date.from(Instant.now()), accountFromId, accountToId, amount)
    }
}
