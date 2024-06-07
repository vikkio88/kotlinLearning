package org.vikkio.app.methods

import org.vikkio.app.AppState
import org.vikkio.app.Context
import org.vikkio.cli.hiddenInput
import org.vikkio.cli.input
import org.vikkio.cli.inputNumber
import org.vikkio.models.Money

val login = { ctx: Context ->
    val username = input("Username: ") ?: ""
    val password = hiddenInput("Password: ") ?: ""
    val user = ctx.db.tryLogin(username, password)
    if (user != null
    ) {
        println("Login successful...")
        ctx.login(user)
    } else {
        println("Invalid login, try again.")
    }
}

val logout = { ctx: Context ->
    println("Logging you out.")
    ctx.changeState(AppState.LoggedOut)
}

val deposit = depositLambda@{ ctx: Context ->
    println("Deposit")
    val wallet = ctx.getLoggedInUser()?.wallet
    if (wallet != null) {
        println("Current balance $wallet")
    }

    if (wallet == null) {
        println("No wallet.")
        //TODO maybe select currency here
        return@depositLambda
    }

    val amount = inputNumber("amount to deposit (${wallet.currency}) > ")
    val withAmount = Money(amount, wallet.currency)
    println("Amount: $withAmount")
    val userId = ctx.getLoggedInUser()!!.id
    val res = ctx.db.tryUpdateWallet(userId, withAmount)
    if (res) println("Deposited successfully.") else println("Deposit failed.")
    ctx.updateUser(ctx.db.getUserById(userId))
}

val withdraw = withdrawLambda@{ ctx: Context ->
    println("Withdrawing")
    val wallet = ctx.getLoggedInUser()?.wallet
    if (wallet != null) {
        println("Current balance $wallet")
    }

    if (wallet == null || wallet.value <= 0) {
        println("Not enough funds to withdraw")
        return@withdrawLambda
    }

    val amount = inputNumber("amount (${wallet.currency}) > ")
    val withAmount = Money(amount, wallet.currency)
    println("Amount: $withAmount")
    val userId = ctx.getLoggedInUser()!!.id
    val res = ctx.db.tryUpdateWallet(userId, withAmount * -1)
    if (res) println("Withdrawn successfully.") else println("Withdrawal failed.")
    ctx.updateUser(ctx.db.getUserById(userId))
}