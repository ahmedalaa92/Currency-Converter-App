package com.example.currencyconverterapp.data.model

import com.google.gson.annotations.SerializedName

data class ExchangeRatesDTO(
    @SerializedName("success") val success: Boolean,
    @SerializedName("timestamp") val timestamp: Long,
    @SerializedName("base") val base: String,
    @SerializedName("date") val date: String,
    @SerializedName("rates") val rates: Map<String, Double> = HashMap()
)

fun ExchangeRatesDTO.toExchangeRateModel(): ExchangeRateModel {
    return ExchangeRateModel(base, date, rates)
}

data class ExchangeRateModel(
    val base: String, val date: String, val rates: Map<String, Double>
)
