package com.example.beardie.currencyholder.data

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.example.beardie.currencyholder.data.enum.TypeOperationEnum
import com.example.beardie.currencyholder.data.model.Balance
import com.example.beardie.currencyholder.data.model.FinanceCurrency
import com.example.beardie.currencyholder.data.model.Transaction
import com.example.beardie.currencyholder.data.model.TransactionCategory
import java.util.*
import javax.inject.Inject

class TransactionRepository @Inject constructor() {

    fun add(transaction: Transaction) {
        HardcodeValues.transactions.add(transaction)
    }

    fun getAll() : LiveData<List<Transaction>> {
        val transactions = MutableLiveData<List<Transaction>>()
        transactions.value = HardcodeValues.transactions
        return transactions
    }

    fun filterByBalanceId(id : String) : LiveData<List<Transaction>> {
        val transactions = MutableLiveData<List<Transaction>>()
        transactions.value = HardcodeValues.transactions.filter { t -> t.balance.id == id }
        return transactions
    }
}