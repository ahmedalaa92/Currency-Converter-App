package com.example.currencyconverterapp.di.modules

import android.content.Context
import com.example.currencyconverterapp.data.local.CurrencyDatabase
import com.example.currencyconverterapp.data.local.dao.CurrenciesDao
import com.example.currencyconverterapp.data.local.repository.LocalRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataBaseModule {

    @Singleton
    @Provides
    fun providesLocalRepository(
        currenciesDao: CurrenciesDao
    ): LocalRepository {
        return LocalRepository(currenciesDao)
    }

    @Singleton
    @Provides
    fun provideCurrenciesDao(appDatabase: CurrencyDatabase): CurrenciesDao {
        return appDatabase.currenciesDao()
    }

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): CurrencyDatabase {
        return CurrencyDatabase.getInstance(context)
    }

}