package com.example.beardie.currencyholder.data.model

import java.util.*

data class FinanceCurrency (
    var id: String = UUID.randomUUID().toString(),
    var symbol: String,
    var title: String,
    var shortTitle: String,
    var imageUri: String
)