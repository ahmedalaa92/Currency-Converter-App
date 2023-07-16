package com.example.currencyconverterapp.data.usecase

import com.example.currencyconverterapp.data.DataState
import com.example.currencyconverterapp.data.model.ExchangeRateModel
import com.example.currencyconverterapp.data.model.toExchangeRateModel
import com.example.currencyconverterapp.data.repository.CurrencyExchangeRatesRepository
import com.example.currencyconverterapp.utils.StringUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RetrieveCurrencyExchangeRatesUseCase @Inject constructor(
    private val repository: CurrencyExchangeRatesRepository, private val stringUtils: StringUtils
) {

    @ExperimentalCoroutinesApi
    suspend operator fun invoke(): Flow<DataState<ExchangeRateModel>> {
        return flow {
            val currencyExchangeRates = repository.getCurrencyExchangeRates()
            currencyExchangeRates.collect { response ->
                when (response) {
                    is DataState.Success -> {
                        response.data?.let {
                            val rates = it.toExchangeRateModel()
                            emit(DataState.success(rates))
                        }
                    }

                    is DataState.Error -> {
                        emit(DataState.error<ExchangeRateModel>(stringUtils.somethingWentWrong()))
                    }
                }
            }
        }.flowOn(Dispatchers.IO)
    }
}