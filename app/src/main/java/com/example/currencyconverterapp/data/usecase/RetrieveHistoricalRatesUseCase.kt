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
    suspend operator fun invoke(
        currencyFrom: String, currencyTo: String
    ): Flow<DataState<out List<HistoricalRateModel?>>> {
        return flow {
            val historicalRates = repository.getHistoricalRates(currencyFrom, currencyTo)
            historicalRates.collect { response ->

                val historicalRateModel =
                    response.filter { it is DataState.Success }.map { rateItem ->
                        val item = rateItem as DataState.Success
                        item.data?.toHistoricalRateModel()
                    }

                if (response.any { it is DataState.Error }) {
                    emit(DataState.error<List<HistoricalRateModel>>(stringUtils.somethingWentWrong()))
                } else {
                    emit(DataState.success(historicalRateModel))
                }
            }
        }.flowOn(Dispatchers.IO)
    }

}