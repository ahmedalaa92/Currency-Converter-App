package com.example.currencyconverterapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.currencyconverterapp.data.local.dao.CurrenciesDao
import com.example.currencyconverterapp.data.local.models.CurrencyNamesEntity
import com.example.currencyconverterapp.utils.Constants.DATABASE_NAME

@Database(
    entities = [CurrencyNamesEntity::class], version = 1, exportSchema = false
)
abstract class CurrencyDatabase : RoomDatabase() {

    abstract fun currenciesDao(): CurrenciesDao

    companion object {
        @Volatile
        private var instance: CurrencyDatabase? = null

        fun getInstance(context: Context): CurrencyDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): CurrencyDatabase {
            return Room.databaseBuilder(context, CurrencyDatabase::class.java, DATABASE_NAME)
                .build()
        }
    }
}