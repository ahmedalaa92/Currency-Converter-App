package com.example.currencyconverterapp.data.model

import com.example.currencyconverterapp.data.local.models.CurrencyNamesEntity
import com.google.gson.annotations.SerializedName

data class CurrenciesDTO(
    @SerializedName("success") val success: Boolean,
    @SerializedName("symbols") val currencies: Map<String, String> = HashMap()
)

fun CurrenciesDTO.toCurrencyModel(): List<CurrencyNamesEntity> {
    val currencyList: MutableList<CurrencyNamesEntity> = ArrayList()
    currencies.map { pair ->
        CurrencyNamesEntity(
            pair.key, pair.value
        )
    }.run {
        currencyList.addAll(this)
    }
    return currencyList
}