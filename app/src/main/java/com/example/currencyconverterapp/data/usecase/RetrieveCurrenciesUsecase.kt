package com.example.currencyconverterapp.data.usecase

import com.example.currencyconverterapp.data.DataState
import com.example.currencyconverterapp.data.local.models.CurrencyNamesEntity
import com.example.currencyconverterapp.data.local.repository.LocalRepository
import com.example.currencyconverterapp.data.model.toCurrencyModel
import com.example.currencyconverterapp.data.repository.CurrencyRepository
import com.example.currencyconverterapp.utils.StringUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RetrieveCurrenciesUseCase @Inject constructor(
    private val repository: CurrencyRepository,
    private val stringUtils: StringUtils,
    private val localRepository: LocalRepository
) {

    @ExperimentalCoroutinesApi
    suspend operator fun invoke(): Flow<DataState<List<CurrencyNamesEntity>>> {
        return flow {
            val currenciesFromDB = localRepository.getAllCurrencyNames()
            if (currenciesFromDB.isNullOrEmpty().not()) {
                emit(DataState.success(currenciesFromDB))
            } else {
                val rates = repository.getCurrencies()
                rates.collect { response ->
                    when (response) {
                        is DataState.Success -> {
                            val currenciesList = response.data?.toCurrencyModel() ?: emptyList()
                            localRepository.insertCurrencyNames(currenciesList)
                            emit(DataState.success(currenciesList))
                        }

                        is DataState.Error -> {
                            emit(DataState.error<List<CurrencyNamesEntity>>(stringUtils.somethingWentWrong()))
                        }
                    }
                }
            }
        }.flowOn(Dispatchers.IO)
    }
}

