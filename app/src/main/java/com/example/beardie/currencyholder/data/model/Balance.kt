package com.example.beardie.currencyholder.data.model

import java.util.*

data class Balance (
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val balance: Double,
    val currency: FinanceCurrency
)