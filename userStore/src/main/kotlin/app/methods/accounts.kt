package org.vikkio.app.methods

import org.vikkio.cli.getFromList
import org.vikkio.cli.input
import org.vikkio.cli.inputNumber
import org.vikkio.models.Account
import org.vikkio.models.AccountFactory
import org.vikkio.models.Money
import org.vikkio.models.enums.Currency

fun createAccount(specifyName: Boolean = false): Account {
    var name: String? = "Main"
    if (specifyName) {
        name = input("Account Name: ")
    }

    println("Select currency:")
    val (_, currency) = getFromList(Currency.entries)
    val amount = inputNumber("funds ($currency): ")

    val account = AccountFactory.makeAccount(Money(amount, currency ?: Currency.US_DOLLAR))
    account.name = name

    return account
}