package com.example.currencyconverterapp.utils

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyconverterapp.data.model.HistoricalRateModel
import com.example.currencyconverterapp.ui.HistoricalRatesListAdapter

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<HistoricalRateModel>?) {
    val adapter = recyclerView.adapter as? HistoricalRatesListAdapter
    adapter?.submitList(data)
}

@BindingAdapter("rates", "fromCurrency", "toCurrency")
fun calculateText(
    textView: TextView, rates: Map<String, Double>, fromCurrency: String, toCurrency: String
) {
    val rateFrom = rates[fromCurrency]
    val rateTo = rates[toCurrency]

    var ratio = 0.0
    if (rateFrom != null && rateTo != null) {
        ratio = rateTo / rateFrom
    }
    textView.text = ratio.toString()
}