package com.example.currencyconverterapp.data.usecase

import com.example.currencyconverterapp.data.local.models.CurrencyExchangeQuery
import javax.inject.Inject

class ConvertCurrencyUseCase @Inject constructor(
) {
    fun convertFromCurrencyToAnotherComparedWithBaseCurrency(
        currencySymbolFrom: String,
        currencySymbolTo: String,
        conversionAmount: Double,
        amountIsFrom: Boolean,
        isSwapped: Boolean,
        rates: Map<String, Double>
    ): CurrencyExchangeQuery {

        val rateFrom = rates[currencySymbolFrom]
        val rateTo = rates[currencySymbolTo]

        var ratio = 0.0
        if (rateFrom != null && rateTo != null) {
            ratio = if (amountIsFrom) rateTo / rateFrom else rateFrom / rateTo
        }

        val result = String.format("%.2f", (ratio * conversionAmount)).toDouble()
        return CurrencyExchangeQuery(
            fromCurrency = if (!isSwapped) currencySymbolFrom else currencySymbolTo,
            toCurrency = if (!isSwapped) currencySymbolTo else currencySymbolFrom,
            fromCurrencyAmount = if (amountIsFrom) conversionAmount else result,
            toCurrencyAmount = if (amountIsFrom) result else conversionAmount,
            date = System.currentTimeMillis()
        )
    }
}