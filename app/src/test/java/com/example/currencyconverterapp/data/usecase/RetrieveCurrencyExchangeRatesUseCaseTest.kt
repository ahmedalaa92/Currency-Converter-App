package com.example.currencyconverterapp.data.usecase

import com.example.currencyconverterapp.MockTestUtil
import com.example.currencyconverterapp.data.DataState
import com.example.currencyconverterapp.data.repository.CurrencyExchangeRatesRepository
import com.example.currencyconverterapp.utils.StringUtils
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@RunWith(JUnit4::class)
class RetrieveCurrencyExchangeRatesUseCaseTest {

    @MockK
    private lateinit var repository: CurrencyExchangeRatesRepository

    @MockK
    private lateinit var stringUtils: StringUtils

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }


    @Test
    fun test_invoking_RetrieveCurrencyUseCase_returns_CurrenciesDTO() = runBlocking {
        // Given
        val retrieveCurrencyExchangeRatesUseCase =
            RetrieveCurrencyExchangeRatesUseCase(repository, stringUtils)
        val givenCurrencyRatesDTO = MockTestUtil.getMockCurrencyRates()

        // When
        coEvery { repository.getCurrencyExchangeRates() }.returns(flowOf(DataState.success(givenCurrencyRatesDTO)))

        // Invoke
        val currencyRatesDTO = retrieveCurrencyExchangeRatesUseCase()

        // Then
        MatcherAssert.assertThat(currencyRatesDTO, CoreMatchers.notNullValue())
    }
}