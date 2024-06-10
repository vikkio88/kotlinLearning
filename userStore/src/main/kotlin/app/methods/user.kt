package org.vikkio.app.methods

import org.vikkio.app.AppState
import org.vikkio.app.Context
import org.vikkio.cli.getFromList
import org.vikkio.cli.hiddenInput
import org.vikkio.cli.input
import org.vikkio.cli.inputNumber
import org.vikkio.models.Money

val login = fun(ctx: Context) {
    val username = input("Username: ") ?: ""
    val password = hiddenInput("Password: ") ?: ""
    val user = ctx.db.tryLogin(username, password)
    if (user == null) {
        println("Invalid login, try again.")
        return
    }

    println("Login successful...")
    ctx.login(user)
}

val logout = { ctx: Context ->
    println("Logging you out.")
    ctx.changeState(AppState.LoggedOut)
}

val deposit = fun(ctx: Context) {
    println("Deposit")
    val mainAccount = ctx.getLoggedInUser()?.selectedAccount
    if (mainAccount != null) {
        println("Current balance $mainAccount")
    }

    if (mainAccount == null) {
        println("No wallet.")
        //TODO maybe select currency here
        return
    }

    val amount = inputNumber("amount to deposit (${mainAccount.currency}) > ")
    val withAmount = Money(amount, mainAccount.currency)
    println("Amount: $withAmount")
    val userId = ctx.getLoggedInUser()!!.id
    val res = ctx.db.tryUpdateWallet(userId, withAmount)
    if (res) println("Deposited successfully.") else println("Deposit failed.")
    ctx.updateUser(ctx.db.getUserById(userId))
}

val withdraw = fun(ctx: Context) {
    println("Withdrawing")
    val mainAccount = ctx.getLoggedInUser()?.selectedAccount
    if (mainAccount != null) {
        println("Current balance $mainAccount")
    }

    if (mainAccount == null || mainAccount.balance.value <= 0) {
        println("Not enough funds to withdraw")
        return
    }

    val amount = inputNumber("amount (${mainAccount.currency}) > ")
    val withAmount = Money(amount, mainAccount.currency)
    println("Amount: $withAmount")
    val userId = ctx.getLoggedInUser()!!.id
    val res = ctx.db.tryUpdateWallet(userId, withAmount * -1)
    if (res) println("Withdrawn successfully.") else println("Withdrawal failed.")
    ctx.updateUser(ctx.db.getUserById(userId))
}

val renameAccount = fun(ctx: Context) {
    println("Rename Account")
    val selectedAccount = ctx.getLoggedInUser()?.selectedAccount
    if (selectedAccount == null) {
        println("No account selected.")
        return
    }

    val newName = input("Add new account name: ")
    selectedAccount.name = newName
    ctx.persistChanges()

}

val selectAccount = fun(ctx: Context) {
    println("Select Account")
    val user = ctx.getLoggedInUser()!!
    if (user.accounts.count() < 2) {
        println("You only have 1 account.")
        return
    }

    val (_, account) = getFromList(user.accounts)
    user.selectedAccount = account
    println("Selected account $account until the end of the session.")
}

val createNewAccount = fun(ctx: Context) {
    println("Creating new account")
    val user = ctx.getLoggedInUser()
    val newAccount = createAccount(specifyName = true)
    user!!.addAccount(newAccount)
    ctx.persistChanges()
    println("New account created")
}