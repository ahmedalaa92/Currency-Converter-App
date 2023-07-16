package com.example.currencyconverterapp.data.local.repository

import com.example.currencyconverterapp.data.local.dao.CurrenciesDao
import com.example.currencyconverterapp.data.local.models.CurrencyNamesEntity
import javax.inject.Inject

class LocalRepository @Inject constructor(
    private var currenciesDao: CurrenciesDao
) {
    fun insertCurrencyNames(currencyNameEntities: List<CurrencyNamesEntity>) {
        currenciesDao.insertCurrencyNames(currencyNameEntities)
    }

    fun getAllCurrencyNames(): List<CurrencyNamesEntity> {
        return currenciesDao.getAllCurrencyNames()
    }
}