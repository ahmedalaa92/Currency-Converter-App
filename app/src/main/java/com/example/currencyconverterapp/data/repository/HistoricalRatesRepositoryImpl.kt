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
import com.example.currencyconverterapp.utils.toString
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class HistoricalRatesRepositoryImpl @Inject constructor(
    private val stringUtils: StringUtils, private val apiService: ApiService
) : HistoricalRatesRepository {

    override suspend fun getHistoricalRates(): Flow<DataState<HistoricalRatesDTO>> {
        return flow {
            apiService.getHistoricalRates(
                getCurrentDate().toString("yyyy-MM-DD"), ""
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