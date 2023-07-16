package com.example.currencyconverterapp.data.repository

import com.example.currencyconverterapp.data.DataState
import com.example.currencyconverterapp.data.model.HistoricalRatesDTO
import com.example.currencyconverterapp.data.remote.ApiService
import com.example.currencyconverterapp.data.remote.message
import com.example.currencyconverterapp.data.remote.onErrorSuspend
import com.example.currencyconverterapp.data.remote.onExceptionSuspend
import com.example.currencyconverterapp.data.remote.onSuccessSuspend
import com.example.currencyconverterapp.utils.StringUtils
import com.example.currencyconverterapp.utils.getCurrentDate
import com.example.currencyconverterapp.utils.getDayBeforeYesterdayDate
import com.example.currencyconverterapp.utils.getYesterdayDate
import com.example.currencyconverterapp.utils.toFormattedString
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.zip
import java.io.IOException
import javax.inject.Inject

class HistoricalRatesRepositoryImpl @Inject constructor(
    private val stringUtils: StringUtils, private val apiService: ApiService
) : HistoricalRatesRepository {
    override suspend fun getHistoricalRates(
        currencyFrom: String, currencyTo: String
    ): Flow<MutableList<DataState<HistoricalRatesDTO>>> {

        val todayResponse =
            createHistoricalRateFlow(getCurrentDate().toFormattedString(), currencyFrom, currencyTo)

        val yesterdayResponse = createHistoricalRateFlow(
            getYesterdayDate().toFormattedString(), currencyFrom, currencyTo
        )

        val beforeYesterdayResponse = createHistoricalRateFlow(
            getDayBeforeYesterdayDate().toFormattedString(), currencyFrom, currencyTo
        )

        return todayResponse.zip(yesterdayResponse) { today, yesterday ->
            mutableListOf(today, yesterday)
        }.zip(beforeYesterdayResponse) { list, beforeYesterday ->
            list.add(beforeYesterday)
            list
        }
    }

    private fun createHistoricalRateFlow(
        date: String, currencyFrom: String, currencyTo: String
    ): Flow<DataState<HistoricalRatesDTO>> {
        return flow {
            apiService.getHistoricalRates(
                date, "$currencyFrom,$currencyTo"
            ).apply {
                this.onSuccessSuspend {
                    data?.let {
                        if (it.success) {
                            emit(DataState.success(it))
                        } else {
                            emit(DataState.error(message = stringUtils.somethingWentWrong()))
                        }
                    } ?: run {
                        emit(DataState.error(message = stringUtils.somethingWentWrong()))
                    }
                }.onErrorSuspend {
                    emit(DataState.error(message()))
                }.onExceptionSuspend {
                    if (this.exception is IOException) {
                        emit(DataState.error(stringUtils.noNetworkErrorMessage()))
                    } else {
                        emit(DataState.error(stringUtils.somethingWentWrong()))
                    }
                }
            }
        }
    }
}