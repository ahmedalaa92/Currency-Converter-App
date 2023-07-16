package com.example.currencyconverterapp.data.local.models


data class CurrencyExchangeQuery(
    val fromCurrency: String,
    val toCurrency: String,
    val fromCurrencyAmount: Double,
    val toCurrencyAmount: Double,
    val date: Long
)
