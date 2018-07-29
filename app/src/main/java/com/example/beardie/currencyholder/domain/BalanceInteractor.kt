package com.example.beardie.currencyholder.domain

import com.example.beardie.currencyholder.data.HardcodeValues
import com.example.beardie.currencyholder.data.TransactionRepository
import com.example.beardie.currencyholder.data.enum.TypeOperationEnum
import com.example.beardie.currencyholder.data.model.Balance
import com.example.beardie.currencyholder.data.model.FinanceCurrency
import com.example.beardie.currencyholder.data.model.Transaction
import com.example.beardie.currencyholder.data.model.TransactionCategory
import java.util.*
import javax.inject.Inject

class BalanceInteractor @Inject constructor(
        private val transactionRepository: TransactionRepository
) {
    fun addTransactions(amount: Double, balance: Balance, currency: FinanceCurrency, date: Date, category: TransactionCategory) {
        transactionRepository.add(Transaction(UUID.randomUUID().toString(), TypeOperationEnum.SUM, amount, balance, currency, date, category))
    }

    fun getSumTransaction(defaultCurrency: FinanceCurrency) : Double {
        return HardcodeValues.transactions.sumByDouble { t ->
            if(t.currency ==  defaultCurrency)
                t.count
            else
                t.count * HardcodeValues.exchange.find { exp -> exp.fromCurrency == t.currency }?.coef!!
        }
    }
}