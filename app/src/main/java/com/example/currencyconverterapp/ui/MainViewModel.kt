package com.example.currencyconverterapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverterapp.data.DataState
import com.example.currencyconverterapp.data.local.models.CurrencyNamesEntity
import com.example.currencyconverterapp.data.model.ExchangeRateModel
import com.example.currencyconverterapp.data.usecase.ConvertCurrencyUseCase
import com.example.currencyconverterapp.data.usecase.RetrieveCurrenciesUseCase
import com.example.currencyconverterapp.data.usecase.RetrieveCurrencyExchangeRatesUseCase
import com.example.currencyconverterapp.utils.StringUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val retrieveCurrenciesUseCase: RetrieveCurrenciesUseCase,
    private val retrieveCurrencyExchangeRatesUseCase: RetrieveCurrencyExchangeRatesUseCase,
    private val convertCurrencyUseCase: ConvertCurrencyUseCase,
    private val stringUtils: StringUtils
) : ViewModel() {

    private val _uiState = MutableLiveData<UIState>()
    val uiStateLiveData: LiveData<UIState>
        get() = _uiState

    private val _currenciesList = MutableLiveData<List<CurrencyNamesEntity>>()
    val currenciesLiveData: LiveData<List<CurrencyNamesEntity>>
        get() = _currenciesList

    private val _ratesList = MutableLiveData<Map<String, Double>>()
    val ratesLiveData: LiveData<Map<String, Double>>
        get() = _ratesList

    private var _currencyExchangeModel = MutableLiveData<ExchangeRateModel>()
    var currencyExchangeModelLiveData: LiveData<ExchangeRateModel> = _currencyExchangeModel

    private val _fromAmountValueLiveData = MutableLiveData<Double>()
    val fromAmountValueLiveData: LiveData<Double>
        get() = _fromAmountValueLiveData

    private val _toAmountValueLiveData = MutableLiveData<Double>()
    val toAmountValueLiveData: LiveData<Double>
        get() = _toAmountValueLiveData

    private val _swapCurrencies = MutableLiveData(false)
    val swapCurrencies: LiveData<Boolean>
        get() = _swapCurrencies

    var currencyFromSelection = ""
        set(value) {
            field = value
            _ratesList.value?.let {

                val conversionQuery =
                    convertCurrencyUseCase.convertFromCurrencyToAnotherComparedWithBaseCurrency(
                        value,
                        currencyToSelection,
                        fromAmountValue,
                        true,
                        _swapCurrencies.value == true,
                        it
                    )
                _toAmountValueLiveData.value = conversionQuery.toCurrencyAmount

            }
        }
    var currencyToSelection = ""
        set(value) {
            field = value
            _ratesList.value?.let {

                val conversionQuery =
                    convertCurrencyUseCase.convertFromCurrencyToAnotherComparedWithBaseCurrency(
                        currencyFromSelection,
                        value,
                        toAmountValue,
                        false,
                        _swapCurrencies.value == true,
                        it
                    )
                _fromAmountValueLiveData.value = conversionQuery.fromCurrencyAmount
            }
        }
    var fromAmountValue = 0.0
        set(value) {
            field = value
            _ratesList.value?.let {
                val conversionQuery =
                    convertCurrencyUseCase.convertFromCurrencyToAnotherComparedWithBaseCurrency(
                        currencyFromSelection,
                        currencyToSelection,
                        value,
                        true,
                        _swapCurrencies.value == true,
                        it
                    )
                _toAmountValueLiveData.value = conversionQuery.toCurrencyAmount

            }
        }
    var toAmountValue = 0.0
        set(value) {
            field = value
            _ratesList.value?.let {
                val conversionQuery =
                    convertCurrencyUseCase.convertFromCurrencyToAnotherComparedWithBaseCurrency(
                        currencyFromSelection,
                        currencyToSelection,
                        value,
                        false,
                        _swapCurrencies.value == true,
                        it
                    )
                _fromAmountValueLiveData.value = conversionQuery.fromCurrencyAmount
            }
        }

    private val _navigationToDetailsScreen = MutableLiveData(false)
    val navigationToDetailsScreen: LiveData<Boolean>
        get() = _navigationToDetailsScreen

    init {
        retrieveCurrencies()
    }

    private fun retrieveCurrencyExchangeRates() {
        _uiState.postValue(LoadingState)
        viewModelScope.launch(Dispatchers.IO) {
            retrieveCurrencyExchangeRatesUseCase.invoke().collect { dataState ->
                withContext(Dispatchers.Main) {
                    when (dataState) {
                        is DataState.Success -> {
                            _uiState.postValue(ContentState)
                            dataState.data?.let { model ->
                                _currencyExchangeModel.postValue(model)
                                _ratesList.postValue(model.rates)
                            }
                        }

                        is DataState.Error -> {
                            _uiState.postValue(ErrorState(stringUtils.somethingWentWrong()))
                        }
                    }
                }
            }
        }
    }

    private fun retrieveCurrencies() {
        _uiState.postValue(LoadingState)
        viewModelScope.launch(Dispatchers.IO) {
            retrieveCurrenciesUseCase.invoke().collect { dataState ->
                withContext(Dispatchers.Main) {
                    when (dataState) {
                        is DataState.Success -> {
                            dataState.data?.let { list ->
                                _currenciesList.postValue(list)
                                currencyFromSelection = list[0].currencyName
                                currencyToSelection = list[0].currencyName
                                retrieveCurrencyExchangeRates()
                            }
                        }

                        is DataState.Error -> {
                            _uiState.postValue(ErrorState(stringUtils.somethingWentWrong()))
                        }
                    }
                }
            }
        }
    }

    fun onSwapButtonClicked() {
        val swapCurrentValue = _swapCurrencies.value
        _swapCurrencies.value = swapCurrentValue?.not()
    }

    fun onDetailsButtonClicked() {
        _navigationToDetailsScreen.value = true
    }

    fun onNavigationToDetailsDone() {
        _navigationToDetailsScreen.value = false
    }

}