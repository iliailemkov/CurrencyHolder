package com.example.beardie.currencyholder.data.model

import com.example.beardie.currencyholder.data.enum.TypeCategoryEnum
import java.util.*

data class TransactionCategory (
        val id: String = UUID.randomUUID().toString(),
        val name: String,
        val type: TypeCategoryEnum,
        val color: Int
)