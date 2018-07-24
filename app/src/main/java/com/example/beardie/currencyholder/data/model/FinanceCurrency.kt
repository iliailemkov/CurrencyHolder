package com.example.beardie.currencyholder.data.model

import java.util.*

data class FinanceCurrency (
    val id: String = UUID.randomUUID().toString(),
    val symbol: String,
    val title: String,
    val shortTitle: String,
    val imageUri: String
)