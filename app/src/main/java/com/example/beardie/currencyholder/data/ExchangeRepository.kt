package com.example.beardie.currencyholder.data

import com.example.beardie.currencyholder.data.model.FinanceCurrency
import javax.inject.Inject

class ExchangeRepository @Inject constructor() {

    fun getExchangeCoef(from: FinanceCurrency, to: FinanceCurrency) = HardcodeValues.exchange.find { e ->
        e.fromCurrency == from && e.toCurrency == to }?.coef
}