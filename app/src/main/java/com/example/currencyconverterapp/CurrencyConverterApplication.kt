package com.example.currencyconverterapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CurrencyConverterApplication : Application() {

    override fun onCreate() {
        super.onCreate()
    }

}