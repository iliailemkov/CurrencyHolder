package com.example.beardie.currencyholder.data.model

import java.util.*

data class ExchangeCurrency (
    var id: String = UUID.randomUUID().toString(),
    var fromCurrency: FinanceCurrency,
    var toCurrency: FinanceCurrency,
    var coef: Double
)