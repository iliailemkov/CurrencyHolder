package com.example.beardie.currencyholder.data.model

import com.example.beardie.currencyholder.data.enum.TypeOperationEnum
import java.util.*

data class Transaction(
    val id: String = UUID.randomUUID().toString(),
    val operation: TypeOperationEnum,
    val count: Double,
    val balance: Balance,
    val currency: FinanceCurrency,
    val date : Date,
    val category: TransactionCategory
)