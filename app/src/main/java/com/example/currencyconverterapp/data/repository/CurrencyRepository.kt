package com.example.currencyconverterapp.data.repository

import com.example.currencyconverterapp.data.DataState
import com.example.currencyconverterapp.data.model.CurrenciesDTO
import kotlinx.coroutines.flow.Flow

interface CurrencyRepository {
    suspend fun getCurrencies(): Flow<DataState<CurrenciesDTO>>
}