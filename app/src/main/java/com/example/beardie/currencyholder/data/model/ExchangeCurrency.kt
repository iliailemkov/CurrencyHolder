package com.example.beardie.currencyholder.data.model

import java.util.*

data class ExchangeCurrency (
    val id: String = UUID.randomUUID().toString(),
    val fromCurrency: FinanceCurrency,
    val toCurrency: FinanceCurrency,
    val coef: Double
)