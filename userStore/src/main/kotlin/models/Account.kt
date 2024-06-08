package org.vikkio.models

import org.vikkio.models.enums.Currency


data class Account(val id: String, val currency: Currency, val amount: Money) {

}