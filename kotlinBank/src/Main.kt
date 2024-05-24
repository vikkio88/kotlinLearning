import models.Currency
import models.Money

fun main() {
   val oneDollar = Money(100, Currency.US_DOLLAR)
   val oneEuro = Money(100, Currency.EURO)
   val onePound = Money(100, Currency.GB_POUND)

    println(oneDollar)
    println(oneEuro)
    println(onePound)
}