package com.example.currencyconverterapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.currencyconverterapp.data.local.models.CurrencyNamesEntity
import com.example.currencyconverterapp.utils.Constants.TABLE_CURRENCY

@Dao
abstract class CurrenciesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertCurrencyNames(currencyNamesEntities: List<CurrencyNamesEntity>)

    @Query("Select * FROM $TABLE_CURRENCY")
    abstract fun getAllCurrencyNames(): List<CurrencyNamesEntity>

}
