package com.example.beardie.currencyholder.data

import com.example.beardie.currencyholder.data.model.FinanceCurrency
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@RunWith(JUnit4::class)
class TransactionRepositoryTest {

    val transactionRepository : TransactionRepository = TransactionRepository()

    @Test
    fun assertEqualSumTransaction() {

        val currency = listOf(
                FinanceCurrency("1", "₽", "Rubles", "RUB", ""),
                FinanceCurrency("2", "$", "Dollars", "USD", "")
        )

        assert(Math.round(transactionRepository.getSumTransaction(currency[0]) * 100.0) / 100.0 == 538.41)
        assert(Math.round(transactionRepository.getSumTransaction(currency[1]) * 100.0) / 100.0 == 8.47)
    }
}