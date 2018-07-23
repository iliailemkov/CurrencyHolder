package com.example.beardie.currencyholder.data

import com.example.beardie.currencyholder.data.model.Transaction
import javax.inject.Inject

class TransactionRepository @Inject constructor() {

    fun getTransactions() : List<Transaction> {
        return listOf()//HardcodeValues.transactions
    }

    fun getTransactions(transactionId: String): Transaction? {
        return HardcodeValues.transactions.find { e -> e.id == transactionId }
    }

    fun refreshSettlement() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}