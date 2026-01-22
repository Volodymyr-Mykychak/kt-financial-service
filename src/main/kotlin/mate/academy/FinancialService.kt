package mate.academy

private const val USD_TO_EUR_RATE = 0.93
private const val USD_TO_GBP_RATE = 0.82
private const val ACCOUNT_NUMBER_LENGTH = 10
private const val CURRENCY_CODE_REGEX = "[A-Z]{3}"

@JvmInline
value class AccountNumber(val value: String) {
    init {
        require(value.length == ACCOUNT_NUMBER_LENGTH && value.all { it.isDigit() })
    }
}

@JvmInline
value class CurrencyAmount(val amount: Double) {
    init {
        require(amount >= 0)
    }
}

@JvmInline
value class CurrencyCode(val code: String) {
    init {
        require(code.matches(Regex(CURRENCY_CODE_REGEX)))
    }
}

@JvmInline
value class TransactionId(val id: String) {
    init {
        require(id.isNotEmpty())
    }
}

class FinancialService {
    fun transferFunds(
        source: AccountNumber,
        destination: AccountNumber,
        amount: CurrencyAmount,
        currencyCode: CurrencyCode,
        transactionId: TransactionId
    ): String {
        val amountInfo = "${amount.amount} ${currencyCode.code}"
        val accountsInfo = "from ${source.value} to ${destination.value}"
        val transactionInfo = "Transaction ID: ${transactionId.id}"

        return "Transferred $amountInfo $accountsInfo. $transactionInfo"
    }

    fun convertCurrency(
        amount: CurrencyAmount,
        fromCurrency: CurrencyCode,
        toCurrency: CurrencyCode
    ): CurrencyAmount {
        val rate = getExchangeRate(fromCurrency, toCurrency)
        return CurrencyAmount(amount.amount * rate)
    }

    private fun getExchangeRate(fromCurrency: CurrencyCode, toCurrency: CurrencyCode): Double {
        return when (fromCurrency.code to toCurrency.code) {
            "USD" to "EUR" -> USD_TO_EUR_RATE
            "USD" to "GBP" -> USD_TO_GBP_RATE
            else -> 1.0
        }
    }
}
