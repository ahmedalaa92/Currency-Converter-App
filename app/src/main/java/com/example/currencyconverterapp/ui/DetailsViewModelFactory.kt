package com.example.currencyconverterapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class DetailsViewModelFactory(private val currencyFrom: String, private val currencyTo: String) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST") return DetailsViewModel(currencyFrom, currencyTo) as T
        }
        throw IllegalArgumentException("Unable to construct viewmodel")
    }
}