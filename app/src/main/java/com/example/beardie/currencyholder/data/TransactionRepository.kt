package com.example.beardie.currencyholder.data

import com.example.beardie.currencyholder.data.enum.TypeOperationEnum
import com.example.beardie.currencyholder.data.model.Balance
import com.example.beardie.currencyholder.data.model.FinanceCurrency
import com.example.beardie.currencyholder.data.model.Transaction
import com.example.beardie.currencyholder.data.model.TransactionCategory
import java.util.*
import javax.inject.Inject

class TransactionRepository @Inject constructor() {

    fun addTransactions(amount: Double, balance: Balance, currency: FinanceCurrency, date: Date, category: TransactionCategory) {
        HardcodeValues.transactions.add(Transaction(UUID.randomUUID().toString(), TypeOperationEnum.SUM, amount, balance, currency, date, category))
    }

    fun getTransactions() : List<Transaction> {
        return HardcodeValues.transactions
    }

    fun getTransactions(balance: Balance) : List<Transaction> {
        return HardcodeValues.transactions.filter { t -> t.balance == balance }
    }

    fun getTransactions(transactionId: String): Transaction? {
        return HardcodeValues.transactions.find { e -> e.id == transactionId }
    }

    fun getExchangeCoef(from: FinanceCurrency, to: FinanceCurrency) = HardcodeValues.exchange.find { e ->
        e.fromCurrency == from && e.toCurrency == to }?.coef

    fun getSumTransaction(defaultCurrency: FinanceCurrency) : Double {
        return HardcodeValues.transactions.sumByDouble { t ->
            if(t.currency ==  defaultCurrency)
                t.count
            else
                t.count * HardcodeValues.exchange.find { exp -> exp.fromCurrency == t.currency }?.coef!!
        }
    }

}