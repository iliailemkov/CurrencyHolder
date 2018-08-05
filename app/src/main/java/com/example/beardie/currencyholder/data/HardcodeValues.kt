package com.example.beardie.currencyholder.data

import android.graphics.Color
import com.example.beardie.currencyholder.data.enum.TypeCategoryEnum
import com.example.beardie.currencyholder.data.model.*
import java.util.Date

object HardcodeValues {

    val currency = listOf(
            FinanceCurrency("1", "₽", "Rubles", "RUB", ""),
            FinanceCurrency("2", "$", "Dollars", "USD", "")
    )

    var category = mutableListOf(
            TransactionCategory("1", "Зарплата", TypeCategoryEnum.INCOME, Color.GREEN),
            TransactionCategory("2", "Стипендия", TypeCategoryEnum.INCOME, Color.GREEN),
            TransactionCategory("3", "Коммунальные платежи", TypeCategoryEnum.OUTGO, Color.RED),
            TransactionCategory("4", "Одежда", TypeCategoryEnum.OUTGO, Color.RED),
            TransactionCategory("5", "Питание", TypeCategoryEnum.OUTGO, Color.RED),
            TransactionCategory("6", "Развлечения", TypeCategoryEnum.OUTGO, Color.RED),
            TransactionCategory("7", "Другое", TypeCategoryEnum.OUTGO, Color.RED)
    )

    val exchange = listOf(
            ExchangeCurrency("1", currency[0], currency[1], 0.016),
            ExchangeCurrency("2", currency[1], currency[0], 63.49)
    )

    var balances = listOf(
            Balance("1", "Наличные", -5000.0, currency[0]),
            Balance("2", "Карта",100.0, currency[1])
    )

    var transactions = mutableListOf(
            Transaction("1", 1000.0, balances[1], currency[0], Date(), category[0]),
            //Transaction("2",  50000.0, balances[0], currency[1], category[0]),
            Transaction("3",99.0, balances[1], currency[0], Date(), category[0]),
            Transaction("4",100.0, balances[1], currency[0], Date(), category[0]),
            Transaction("5",-90.0, balances[1], currency[0], Date(), category[3]),
            Transaction("6",-99.0, balances[1], currency[0], Date(), category[4]),
            Transaction("7",-100.0, balances[1], currency[0], Date(), category[4]),
            Transaction("8",-90.0, balances[1], currency[0], Date(), category[5]),
            Transaction("9",99.0, balances[1], currency[0], Date(), category[0]),
            Transaction("10",-133.0, balances[1], currency[0], Date(), category[5])
    )
}