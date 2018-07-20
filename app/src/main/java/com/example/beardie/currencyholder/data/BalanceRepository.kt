package com.example.beardie.currencyholder.data

import com.example.beardie.currencyholder.data.enum.CurrencyEnum
import com.example.beardie.currencyholder.data.model.Balance

class BalanceRepository {

    //hardcode values
    private val balances = listOf(
            Balance("1", 5000, CurrencyEnum.RUB, true),
            Balance("2", 100, CurrencyEnum.USD, false)
    )

    fun getBalances() : List<Balance> {
        return balances
    }
}