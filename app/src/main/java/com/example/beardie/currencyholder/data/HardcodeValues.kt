package com.example.beardie.currencyholder.data

import com.example.beardie.currencyholder.data.enum.TypeOperationEnum
import com.example.beardie.currencyholder.data.model.Balance
import com.example.beardie.currencyholder.data.model.ExchangeCurrency
import com.example.beardie.currencyholder.data.model.FinanceCurrency
import com.example.beardie.currencyholder.data.model.Transaction

object HardcodeValues {

    val currency = listOf(
            FinanceCurrency("1", "₽", "Rubles", "RUB", ""),
            FinanceCurrency("2", "$", "Dollars", "USD", "")
    )

    val exchange = listOf(
            ExchangeCurrency("1", currency[0], currency[1], 0.016),
            ExchangeCurrency("2", currency[1], currency[0], 63.49)
    )

    val transactions = listOf(
            Transaction("1", TypeOperationEnum.SUM, 100.0, currency[0]),
            Transaction("2", TypeOperationEnum.SUM, -90.0, currency[1]),
            Transaction("3", TypeOperationEnum.SUM, 99.0, currency[1]),
            Transaction("4", TypeOperationEnum.SUM, -133.0, currency[0])
    )

    val balances = listOf(
            Balance("1", -5000.0, currency[0], true),
            Balance("2", 100.0, currency[1], false)
    )
}