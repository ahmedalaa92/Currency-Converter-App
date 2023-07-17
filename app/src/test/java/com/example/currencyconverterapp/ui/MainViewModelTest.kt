package com.example.currencyconverterapp.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.currencyconverterapp.data.repository.CurrencyRepository
import com.example.currencyconverterapp.data.usecase.ConvertCurrencyUseCase
import com.example.currencyconverterapp.data.usecase.RetrieveCurrenciesUseCase
import com.example.currencyconverterapp.data.usecase.RetrieveCurrencyExchangeRatesUseCase
import com.example.currencyconverterapp.utils.StringUtils
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class MainViewModelTest {


    private lateinit var viewModel: MainViewModel

    @get:Rule
    val instantTaskExecRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var currencyRepository: CurrencyRepository

    private var retrieveCurrenciesUseCase: RetrieveCurrenciesUseCase = mockk()

    private var retrieveCurrencyExchangeRatesUseCase: RetrieveCurrencyExchangeRatesUseCase = mockk()

    @MockK
    private lateinit var convertCurrencyUseCase: ConvertCurrencyUseCase

    @MockK
    private lateinit var stringUtils: StringUtils

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        viewModel = MainViewModel(
            retrieveCurrenciesUseCase,
            retrieveCurrencyExchangeRatesUseCase,
            convertCurrencyUseCase,
            stringUtils
        )
    }

    @Test
    fun testOnDetailsButtonClicked_VerifyNavigationToDetailsScreenIsTrue() {
        viewModel.onDetailsButtonClicked()
        Assert.assertEquals(true, viewModel.navigationToDetailsScreen.value)
    }

    @Test
    fun testOnSwapButtonClicked_VerifySwapCurrenciesIsTrue() {
        Assert.assertEquals(false, viewModel.swapCurrencies.value)
        viewModel.onSwapButtonClicked()
        Assert.assertEquals(true, viewModel.swapCurrencies.value)
    }

    @Test
    fun testRetrieveCurrencies_VerifyRetrieveCurrencyUseCaseInvoked() = runBlocking {
        coEvery { retrieveCurrenciesUseCase.invoke() }.returns(
            mockk(relaxed = true)
        )
        coVerify { retrieveCurrenciesUseCase.invoke() }
    }
}

