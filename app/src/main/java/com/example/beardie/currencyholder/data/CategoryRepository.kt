package com.example.beardie.currencyholder.data

import com.example.beardie.currencyholder.data.model.FinanceCurrency
import com.example.beardie.currencyholder.data.model.Transaction
import com.example.beardie.currencyholder.data.model.TransactionCategory
import javax.inject.Inject

class CategoryRepository @Inject constructor() {

    fun getCategories() : List<TransactionCategory> {
        return HardcodeValues.category
    }

    fun addCategory(category : TransactionCategory) : Unit {
        HardcodeValues.category.add(category)
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