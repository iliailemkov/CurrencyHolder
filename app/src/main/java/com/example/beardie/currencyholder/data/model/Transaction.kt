package com.example.beardie.currencyholder.data.model

import com.example.beardie.currencyholder.data.enum.TypeOperationEnum
import java.util.*

data class Transaction(
    var id: String = UUID.randomUUID().toString(),
    var operation: TypeOperationEnum,
    var count: Double,
    var currency: FinanceCurrency
)