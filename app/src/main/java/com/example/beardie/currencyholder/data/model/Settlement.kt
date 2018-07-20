package com.example.beardie.currencyholder.data.model

import com.example.beardie.currencyholder.data.enum.CurrencyEnum
import com.example.beardie.currencyholder.data.enum.TypeOperationEnum
import java.util.*

data class Settlement(
        var id: String = UUID.randomUUID().toString(),
        var operation: TypeOperationEnum,
        var count: Long,
        var currency: CurrencyEnum
)