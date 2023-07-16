package com.example.currencyconverterapp.di.modules

import android.app.Application
import com.example.currencyconverterapp.data.remote.ApiService
import com.example.currencyconverterapp.data.repository.CurrencyExchangeRatesRepository
import com.example.currencyconverterapp.data.repository.CurrencyExchangeRatesRepositoryImpl
import com.example.currencyconverterapp.data.repository.CurrencyRepository
import com.example.currencyconverterapp.data.repository.CurrencyRepositoryImpl
import com.example.currencyconverterapp.data.repository.HistoricalRatesRepository
import com.example.currencyconverterapp.data.repository.HistoricalRatesRepositoryImpl
import com.example.currencyconverterapp.utils.StringUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * The Dagger Module for providing repository instances.
 */
@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideStringUtils(app: Application): StringUtils {
        return StringUtils(app)
    }

    @Singleton
    @Provides
    fun provideCurrencyRepository(
        stringUtils: StringUtils, apiService: ApiService
    ): CurrencyRepository {
        return CurrencyRepositoryImpl(stringUtils, apiService)
    }

    @Singleton
    @Provides
    fun provideCurrencyExchangeRatesRepository(
        stringUtils: StringUtils, apiService: ApiService
    ): CurrencyExchangeRatesRepository {
        return CurrencyExchangeRatesRepositoryImpl(stringUtils, apiService)
    }

    @Singleton
    @Provides
    fun provideHistoricalRatesRepository(
        stringUtils: StringUtils, apiService: ApiService
    ): HistoricalRatesRepository {
        return HistoricalRatesRepositoryImpl(stringUtils, apiService)
    }
}
