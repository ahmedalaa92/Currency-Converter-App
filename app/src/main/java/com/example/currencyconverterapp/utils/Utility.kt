package com.example.currencyconverterapp.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@SuppressLint("WeekBasedYear")
fun Date.toFormattedString(
    format: String = "yyyy-MM-dd", locale: Locale = Locale.getDefault()
): String {
    val formatter = SimpleDateFormat(format, locale)
    return formatter.format(this)
}

fun getCurrentDate() = Date()
fun getYesterdayDate(): Date {
    val calendar: Calendar = Calendar.getInstance()
    calendar.time = getCurrentDate()
    calendar.add(Calendar.DATE, -1)
    return calendar.time
}
fun getDayBeforeYesterdayDate(): Date {
    val calendar: Calendar = Calendar.getInstance()
    calendar.time = getCurrentDate()
    calendar.add(Calendar.DATE, -2)
    return calendar.time
}
