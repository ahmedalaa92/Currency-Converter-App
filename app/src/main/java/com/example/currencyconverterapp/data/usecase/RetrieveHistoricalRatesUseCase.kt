package com.example.currencyconverterapp.data.usecase

import com.example.currencyconverterapp.data.DataState
import com.example.currencyconverterapp.data.model.HistoricalRateModel
import com.example.currencyconverterapp.data.model.toHistoricalRateModel
import com.example.currencyconverterapp.data.repository.HistoricalRatesRepository
import com.example.currencyconverterapp.utils.StringUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RetrieveHistoricalRatesUseCase @Inject constructor(
    private val repository: HistoricalRatesRepository, private val stringUtils: StringUtils
) {

    @ExperimentalCoroutinesApi
    suspend operator fun invoke(): Flow<DataState<HistoricalRateModel>> {
        return flow {
            val currencyExchangeRates = repository.getHistoricalRates()
            currencyExchangeRates.collect { response ->
                when (response) {
                    is DataState.Success -> {
                        response.data?.let {
                            val rates = it.toHistoricalRateModel()
                            emit(DataState.success(rates))
                        }
                    }

                    is DataState.Error -> {
                        emit(DataState.error<HistoricalRateModel>(stringUtils.somethingWentWrong()))
                    }
                }
            }
        }.flowOn(Dispatchers.IO)
    }

}