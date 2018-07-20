package com.example.beardie.currencyholder.data.model

import com.example.beardie.currencyholder.data.enum.CurrencyEnum
import java.util.*

data class Balance(
        var id: String = UUID.randomUUID().toString(),
        var balance: Long,
        var currency: CurrencyEnum,
        var active: Boolean
)