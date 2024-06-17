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
    val selectedAccount = ctx.getLoggedInUser()?.selectedAccount
    if (selectedAccount != null) {
        println("Current balance $selectedAccount")
    }

    if (selectedAccount == null) {
        println("No account selected.")
        return
    }

    val amount = inputNumber("amount to deposit (${selectedAccount.currency}) > ")
    val withAmount = Money(amount, selectedAccount.currency)
    println("Amount: $withAmount")
    val userId = ctx.getLoggedInUser()!!.id
    val res = ctx.db.tryUpdateUserAccount(userId, withAmount, selectedAccount.id)
    if (res) println("Deposited successfully.") else println("Deposit failed.")
    ctx.refreshLoggedInUser(ctx.db.getUserById(userId))
}

val withdraw = fun(ctx: Context) {
    println("Withdrawing")
    val selectedAccount = ctx.getLoggedInUser()?.selectedAccount
    if (selectedAccount != null) {
        println("Current balance $selectedAccount")
    }

    if (selectedAccount == null) {
        println("No account selected.")
        return
    }

    if (selectedAccount.balance.value <= 0) {
        println("Not enough funds to withdraw")
        return
    }

    val amount = inputNumber("amount (${selectedAccount.currency}) > ")
    val withAmount = Money(amount, selectedAccount.currency)
    println("Amount: $withAmount")
    val userId = ctx.getLoggedInUser()!!.id
    val res = ctx.db.tryUpdateUserAccount(userId, withAmount * -1, selectedAccount.id)
    if (res) println("Withdrawn successfully.") else println("Withdrawal failed.")
    ctx.refreshLoggedInUser(ctx.db.getUserById(userId))
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
    ctx.refreshLoggedInUser(ctx.getLoggedInUser())
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
    ctx.refreshLoggedInUser(user)
    println("New account created")
}

val changePassword = fun(ctx: Context) {
    println("Change Password")
    val user = ctx.getLoggedInUser()
    val oldPwd = hiddenInput("Old password: ")
    val newPwd = hiddenInput("New password: ")
    val newPwdConfirm = hiddenInput("New password (confirm): ")

    if (newPwd != newPwdConfirm) {
        println("The two new password do not match. try again.")
    }

    if (!ctx.db.setUserPassword(user!!.id, newPwd ?: "", oldPwd ?: "")) {
        println("Changing password failed, maybe you mistyped your old password?")
        return
    }

    println("Password changed.")
}

val accountInfo = fun(ctx: Context) {
    val selectedAccount = ctx.getLoggedInUser()?.selectedAccount
    if (selectedAccount == null) {
        println("No Account selected.")
        return
    }

    println(selectedAccount.toString(full = true))
}

val moveFundsBetweenAccounts = fun(ctx: Context) {
    println("Move funds between accounts")
    val user = ctx.getLoggedInUser()!!
    if (user.accounts.count() < 2) {
        println("No accounts to move your funds to.")
        return
    }
    val selectedAccount = user.selectedAccount
    if (selectedAccount == null) {
        println("No account selected.")
        return
    }

    val amount = inputNumber("Amount to transfer (${selectedAccount.balance.currency}): ")
    println("Select account to move funds to:")
    val (_, account) = getFromList(user.accounts.filter { it.id != selectedAccount.id })
    if (account == null) {
        println("No account selected.")
        return
    }
    if (ctx.db.transferFunds(selectedAccount.id, account.id, Money(amount, selectedAccount.currency))) {
        ctx.refreshLoggedInUser(ctx.db.getUserById(user.id))
        println("Money moved successfully.")
        return
    }

    println("Could not move the funds.")

}

val pay = fun(ctx: Context) {
    println("Payment")
    val user = ctx.getLoggedInUser()!!
    val fromAccount = user.selectedAccount
    if (fromAccount == null) {
        println("No account selected, retry.")
        return
    }
    println("using your selected account: '${fromAccount}'")
    println("Specify bank account to move money to:")
    val toId = input()
    val amount = inputNumber("amount (${fromAccount.currency}): ")
    if (ctx.db.transferFunds(fromAccount.id, toId ?: "", Money(amount, fromAccount.currency))) {
        ctx.refreshLoggedInUser(ctx.db.getUserById(user.id))
        println("Payment successful.")
        return
    }

    println("Payment rejected.")
}