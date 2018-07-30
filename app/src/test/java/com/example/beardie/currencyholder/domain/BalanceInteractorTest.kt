package com.example.beardie.currencyholder.domain

import com.example.beardie.currencyholder.data.BalanceRepository
import com.example.beardie.currencyholder.data.ExchangeRepository
import com.example.beardie.currencyholder.data.HardcodeValues
import com.example.beardie.currencyholder.data.TransactionRepository
import com.example.beardie.currencyholder.data.model.FinanceCurrency
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class BalanceInteractorTest {

    val balanceInteractor : BalanceInteractor = BalanceInteractor(
            BalanceRepository(),
            TransactionRepository(),
            ExchangeRepository())

    @Test
    fun assertEqualSumTransaction() {

        val currency = listOf(
                FinanceCurrency("1", "₽", "Rubles", "RUB", ""),
                FinanceCurrency("2", "$", "Dollars", "USD", "")
        )

        val c0 = HardcodeValues.transactions.sumByDouble { t ->
            if (t.currency == currency[0])
                t.count
            else
                t.count * HardcodeValues.exchange.find { exp -> exp.fromCurrency == t.currency }?.coef!!
        }

        val c1 = HardcodeValues.transactions.sumByDouble { t ->
            if (t.currency == currency[1])
                t.count
            else
                t.count * HardcodeValues.exchange.find { exp -> exp.fromCurrency == t.currency }?.coef!!
        }

        assert(balanceInteractor.getSumTransaction(currency[0])  == c0)
        assert(balanceInteractor.getSumTransaction(currency[1])  == c1)
    }


}