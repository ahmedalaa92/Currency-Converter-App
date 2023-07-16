package com.example.currencyconverterapp.data.repository

import com.example.currencyconverterapp.data.DataState
import com.example.currencyconverterapp.data.model.ExchangeRatesDTO
import kotlinx.coroutines.flow.Flow

interface CurrencyExchangeRatesRepository {
    suspend fun getCurrencyExchangeRates(): Flow<DataState<ExchangeRatesDTO>>
}