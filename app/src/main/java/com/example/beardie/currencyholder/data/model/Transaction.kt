package com.example.beardie.currencyholder.data.model

import java.util.*

data class Transaction(
    val id: String = UUID.randomUUID().toString(),
    val count: Double,
    val balance: Balance,
    val currency: FinanceCurrency,
    val date : Date,
    val category: TransactionCategory
)