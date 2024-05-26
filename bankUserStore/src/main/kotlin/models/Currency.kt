package bankUserStore.models

enum class Currency(private val symbol: String) {
    US_DOLLAR("$"),
    GB_POUND("£"),
    EURO("€");

    override fun toString(): String {
        return symbol;
    }

}
