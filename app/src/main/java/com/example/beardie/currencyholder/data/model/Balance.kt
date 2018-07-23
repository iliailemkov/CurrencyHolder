package com.example.beardie.currencyholder.data.model

import java.util.*

data class Balance (
    var id: String = UUID.randomUUID().toString(),
    var balance: Double,
    var currency: FinanceCurrency,
    var active: Boolean
)