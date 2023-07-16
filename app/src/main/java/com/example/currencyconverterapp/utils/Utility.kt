package com.example.currencyconverterapp.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
    val formatter = SimpleDateFormat(format, locale)
    return formatter.format(this)
}

fun getCurrentDate() = Date()
