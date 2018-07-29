package com.example.beardie.currencyholder.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import com.example.beardie.currencyholder.data.BalanceRepository
import com.example.beardie.currencyholder.data.CurrencyRepository
import com.example.beardie.currencyholder.data.SharedPrefRepository
import com.example.beardie.currencyholder.data.TransactionRepository
import com.example.beardie.currencyholder.domain.SummaryInteractor
import javax.inject.Inject

class FinanceViewModel @Inject constructor(
        context: Application,
        private val transactionRepository: TransactionRepository,
        private val balanceRepository: BalanceRepository,
        private val currencyRepository: CurrencyRepository,
        private val summaryInteractor: SummaryInteractor,
        private val prefRepository: SharedPrefRepository
) : AndroidViewModel(context) {

    var currentBalance = MutableLiveData<String>()
        set(value) {
            currentBalance.value = value.value
        }

    val balance = Transformations.switchMap(currentBalance) { id -> balanceRepository.findById(id)}

    val currencyList by lazy { currencyRepository.getAll() }

    val balances by lazy { balanceRepository.getAll() }

    val transactions = Transformations.switchMap(currentBalance) { id -> transactionRepository.filterByBalanceId(id) }

    val summary = Transformations.switchMap(currentBalance) { summaryInteractor.getPieChartValues(balance.value?: balanceRepository.getAll().value!![0]) }

}