package mate.academy

const val GBP = 0.82
const val EUR = 0.93

@JvmInline
value class AccountNumber(val accountNumber: String) {
    init {
        require(accountNumber.matches(Regex("\\d{10}")))
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
        require(code.matches(Regex("[A-Z]{3}")))
    }
}

@JvmInline
value class TransactionId(val transactionId: String) {
    init {
        require(transactionId.isNotEmpty())
    }
}

class FinancialService {
    fun transferFunds(
        source: AccountNumber,
        destination: AccountNumber,
        amount: CurrencyAmount,
        currencyCode: CurrencyCode,
        transactionId: TransactionId
    ) : String {
        return "Transferred ${amount.amount} ${currencyCode.code} " +
                "from ${source.accountNumber} to ${destination.accountNumber}. " +
                "Transaction ID: ${transactionId.transactionId}"
    }

    fun convertCurrency(
        amount: CurrencyAmount,
        fromCurrency: CurrencyCode,
        toCurrency: CurrencyCode
    ): CurrencyAmount {
        val exchangeRate = getExchangeRate(fromCurrency, toCurrency)
        return CurrencyAmount(amount.amount * exchangeRate)
    }

    private fun getExchangeRate(fromCurrency: CurrencyCode, toCurrency: CurrencyCode): Double {
        // Placeholder exchange rate - in a real application, you'd fetch this from a financial API
        return when {
            fromCurrency.code == "USD" && toCurrency.code == "EUR" -> EUR
            fromCurrency.code == "USD" && toCurrency.code == "GBP" -> GBP
            else -> 1.0
        }
    }
}
