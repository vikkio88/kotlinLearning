package org.vikkio.exercises

import org.vikkio.interfaces.Exercise

enum class Buttons {
    A, B, X, Y
}

class ControlFlows1 : Exercise {
    override val title: String
        get() = "Control Flows 1"

    /*
Using a when expression, update the following program so that when you input the names of
Game Boy buttons,
the actions are printed to output.

Button      Action
    A       Yes
    B       No
    X       Menu
    Y       Nothing
    Other   There is no such button
     */
    override fun body() {
        val buttons = listOf(Buttons.A, Buttons.B, Buttons.X, Buttons.Y)

        for (btn in buttons) {
            println("Button: $btn -> action: ${action(btn)}")
        }


        println("NonButton Int: ${action(1)}")
        println("NonButton Char: ${action('c')}")
    }

    private fun action(btn: Any): String {
        return when(btn) {
            Buttons.A -> "Yes"
            Buttons.B -> "No"
            Buttons.X -> "Menu"
            Buttons.Y -> "Nothing"
            else -> "There is no such button"
        }

    }
}