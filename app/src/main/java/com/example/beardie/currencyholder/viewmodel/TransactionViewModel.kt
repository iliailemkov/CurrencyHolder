package com.example.beardie.currencyholder.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import com.example.beardie.currencyholder.data.BalanceRepository
import com.example.beardie.currencyholder.data.CategoryRepository
import com.example.beardie.currencyholder.data.CurrencyRepository
import com.example.beardie.currencyholder.data.TransactionRepository
import com.example.beardie.currencyholder.data.enum.TypeCategoryEnum
import com.example.beardie.currencyholder.data.model.Balance
import com.example.beardie.currencyholder.data.model.FinanceCurrency
import com.example.beardie.currencyholder.data.model.Transaction
import com.example.beardie.currencyholder.data.model.TransactionCategory
import com.example.beardie.currencyholder.domain.BalanceInteractor
import java.util.*
import javax.inject.Inject

class TransactionViewModel @Inject constructor(
        context: Application,
        private val balanceInteractor: BalanceInteractor,
        private val balanceRepository: BalanceRepository,
        private val currencyRepository: CurrencyRepository,
        private val categoryRepository: CategoryRepository
) : AndroidViewModel(context) {

    var filter = MutableLiveData<Int>()// prefRepository.getDefaultCurrency()
        set(value) {
            filter.value = value.value
        }

    val currencyList by lazy { currencyRepository.getAll() }

    val balances by lazy { balanceRepository.getAll() }

    val categories = Transformations.switchMap(filter) { id ->
        categoryRepository.filterByType(id)
    }?: categoryRepository.getAll()

    fun addTransaction(amount: Double, balance: Balance, currency: FinanceCurrency, date: Date, category: TransactionCategory) {
        balanceInteractor.addTransactions(amount, balance, currency, date, category)
    }
}