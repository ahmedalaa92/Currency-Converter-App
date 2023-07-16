package com.example.currencyconverterapp.data.remote

import com.example.currencyconverterapp.data.model.CurrenciesDTO
import com.example.currencyconverterapp.data.model.ExchangeRatesDTO
import com.example.currencyconverterapp.data.model.HistoricalRatesDTO
import com.example.currencyconverterapp.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.text.DateFormatSymbols

interface ApiService {

    @GET("symbols")
    suspend fun getCurrencies(
        @Query("access_key") apiKey: String = Constants.API_KEY
    ): ApiResponse<CurrenciesDTO>

    @GET("latest")
    suspend fun getExchangeRates(
        @Query("access_key") apiKey: String = Constants.API_KEY
    ): ApiResponse<ExchangeRatesDTO>

    @GET("{date}")
    suspend fun getHistoricalRates(
        @Path("date") date: String,
        @Query("symbols") symbols: String,
        @Query("access_key") apiKey: String = Constants.API_KEY
    ): ApiResponse<HistoricalRatesDTO>

}