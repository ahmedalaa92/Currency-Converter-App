package com.example.currencyconverterapp.utils

import android.app.Application
import com.example.currencyconverterapp.R

class StringUtils(private val appContext: Application) {
    fun noNetworkErrorMessage() = appContext.getString(R.string.message_no_internet_connection)
    fun somethingWentWrong() = appContext.getString(R.string.message_something_went_wrong)
}
