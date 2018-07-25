package com.example.beardie.currencyholder.data.model

import java.util.*

data class Balance (
    val id: String = UUID.randomUUID().toString(),
    val balance: Double,
    val currency: FinanceCurrency,
    val active: Boolean
)