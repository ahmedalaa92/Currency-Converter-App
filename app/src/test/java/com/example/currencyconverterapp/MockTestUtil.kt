package com.example.currencyconverterapp

import com.example.currencyconverterapp.data.model.CurrenciesDTO
import com.example.currencyconverterapp.data.model.ExchangeRatesDTO

class MockTestUtil {
    companion object {
        fun getMockCurrencyDTO(): CurrenciesDTO {
            val currencies: HashMap<String, String> = HashMap()
            currencies["PKR"] = "Pakistani Rupees"
            currencies["AFG"] = "Afghani"
            return CurrenciesDTO(true, currencies)
        }

        fun getMockCurrencyRates(): ExchangeRatesDTO {
            val currencies: HashMap<String, Double> = HashMap()
            currencies["PKR"] = 12.3
            currencies["AFG"] = 32.5
            return ExchangeRatesDTO(true, 1231231231, "PKR", "1231231231", currencies)
        }

    }
}
