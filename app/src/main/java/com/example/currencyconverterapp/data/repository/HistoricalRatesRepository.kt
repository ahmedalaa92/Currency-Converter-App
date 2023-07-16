package com.example.currencyconverterapp.data.repository

import com.example.currencyconverterapp.data.DataState
import com.example.currencyconverterapp.data.model.HistoricalRatesDTO
import kotlinx.coroutines.flow.Flow

interface HistoricalRatesRepository {
    suspend fun getHistoricalRates(): Flow<DataState<HistoricalRatesDTO>>
}