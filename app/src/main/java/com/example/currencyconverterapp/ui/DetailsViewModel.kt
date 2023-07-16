package com.example.currencyconverterapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverterapp.data.DataState
import com.example.currencyconverterapp.data.model.HistoricalRateModel
import com.example.currencyconverterapp.data.usecase.RetrieveHistoricalRatesUseCase
import com.example.currencyconverterapp.utils.StringUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val retrieveHistoricalRatesUseCase: RetrieveHistoricalRatesUseCase,
    private val stringUtils: StringUtils
) : ViewModel() {

    private val _uiState = MutableLiveData<UIState>()
    val uiStateLiveData: LiveData<UIState>
        get() = _uiState

    private val _currencyExchangeModel = MutableLiveData<List<HistoricalRateModel?>>()
    val currencyExchangeModelLiveData: LiveData<List<HistoricalRateModel?>>
        get() = _currencyExchangeModel

    fun retrieveHistoricalRates(currencyFrom: String, currencyTo: String) {
        _uiState.postValue(LoadingState)
        viewModelScope.launch(Dispatchers.IO) {
            retrieveHistoricalRatesUseCase.invoke(currencyFrom, currencyTo).collect { dataState ->
                withContext(Dispatchers.Main) {
                    when (dataState) {
                        is DataState.Success -> {
                            _uiState.postValue(ContentState)
                            dataState.data?.let { model ->
                                _currencyExchangeModel.postValue(model)
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
}