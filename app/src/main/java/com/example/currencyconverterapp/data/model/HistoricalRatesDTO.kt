package com.example.currencyconverterapp.data.model

import com.google.gson.annotations.SerializedName

data class HistoricalRatesDTO(
    @SerializedName("success") val success: Boolean,
    @SerializedName("timestamp") val timestamp: Long,
    @SerializedName("historical") val historical: Boolean,
    @SerializedName("base") val base: String,
    @SerializedName("date") val date: String,
    @SerializedName("rates") val rates: Map<String, Double> = HashMap()
)

fun HistoricalRatesDTO.toHistoricalRateModel(): HistoricalRateModel {
    return HistoricalRateModel(base, date, rates)
}

data class HistoricalRateModel(
    val base: String, val date: String, val rates: Map<String, Double>
)

