package com.example.currencyconverterapp.data.usecase

import com.example.currencyconverterapp.MockTestUtil
import com.example.currencyconverterapp.data.DataState
import com.example.currencyconverterapp.data.local.repository.LocalRepository
import com.example.currencyconverterapp.data.repository.CurrencyRepository
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
class RetrieveCurrenciesUsecaseTest {

    @MockK
    private lateinit var repository: CurrencyRepository

    @MockK
    private lateinit var stringUtils: StringUtils

    @MockK
    private lateinit var localRepository: LocalRepository


    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun test_invoking_RetrieveCurrencyUseCase_returns_CurrenciesDTO() = runBlocking {
        // Given
        val retrieveCurrencyUseCase =
            RetrieveCurrenciesUseCase(repository, stringUtils, localRepository)
        val givenCurrencyDTO = MockTestUtil.getMockCurrencyDTO()

        // When
        coEvery { repository.getCurrencies() }.returns(flowOf(DataState.success(givenCurrencyDTO)))

        // Invoke
        val currencyDTO = retrieveCurrencyUseCase()

        // Then
        MatcherAssert.assertThat(currencyDTO, CoreMatchers.notNullValue())
    }
}