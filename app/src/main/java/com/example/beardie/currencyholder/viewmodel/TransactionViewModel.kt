package com.example.beardie.currencyholder.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.example.beardie.currencyholder.data.BalanceRepository
import com.example.beardie.currencyholder.data.CategoryRepository
import com.example.beardie.currencyholder.data.CurrencyRepository
import com.example.beardie.currencyholder.data.TransactionRepository
import com.example.beardie.currencyholder.data.model.Balance
import com.example.beardie.currencyholder.data.model.FinanceCurrency
import com.example.beardie.currencyholder.data.model.Transaction
import com.example.beardie.currencyholder.data.model.TransactionCategory
import java.util.*
import javax.inject.Inject

class TransactionViewModel @Inject constructor(
        context: Application,
        private val transactionRepository: TransactionRepository,
        private val balanceRepository: BalanceRepository,
        private val currencyRepository: CurrencyRepository,
        private val categoryRepository: CategoryRepository
) : AndroidViewModel(context) {

    var currentCurrency : Int = 0// prefRepository.getDefaultCurrency()
        //set(value) {
        //    balance.value = balanceRepository.getBalance(currencyRepository.getCurrencyList()[value])
       // }

    private var currencyList = MutableLiveData<List<FinanceCurrency>>()

    fun getCurrencyShortNameList(): LiveData<List<FinanceCurrency>> {
        if (currencyList.value == null) {
            currencyList.value = currencyRepository.getCurrencyList()
        }
        return currencyList
    }

    private var balance = MutableLiveData<Balance>()

    fun getBalance(): LiveData<Balance> {
        if (balance.value == null) {
            balance.value = balanceRepository.getBalances()[0]
        }
        balance.value = balance.value
        return balance
    }

    private var transactions = MutableLiveData<List<Transaction>>()

    fun getTransactions(): LiveData<List<Transaction>> {
        if (transactions.value == null) {
            transactions.value = transactionRepository.getTransactions()
        }
        return transactions
    }

    private var categories = MutableLiveData<List<TransactionCategory>>()

    fun getCategories(): LiveData<List<TransactionCategory>> {
        if (categories.value == null) {
            categories.value = categoryRepository.getCategories()
        }
        return categories
    }

    fun addTransaction(amount: Double, balance: Balance, currency: FinanceCurrency, date: Date, category: TransactionCategory) {
        transactionRepository.addTransactions(amount, balance, currency, date, category)
    }
}