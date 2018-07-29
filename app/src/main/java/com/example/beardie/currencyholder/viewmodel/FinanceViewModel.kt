package com.example.beardie.currencyholder.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import com.example.beardie.currencyholder.data.BalanceRepository
import com.example.beardie.currencyholder.data.TransactionRepository
import com.example.beardie.currencyholder.domain.SummaryInteractor
import javax.inject.Inject

class FinanceViewModel @Inject constructor(
        context: Application,
        private val transactionRepository: TransactionRepository,
        private val balanceRepository: BalanceRepository,
        private val summaryInteractor: SummaryInteractor
) : AndroidViewModel(context) {

    var currentBalance = MutableLiveData<String>()
        set(value) {
            currentBalance.value = value.value
        }

    val balance = Transformations.switchMap(currentBalance) { id -> balanceRepository.findById(id)}

    val balances by lazy { balanceRepository.getAll() }

    val transactions = Transformations.switchMap(currentBalance) { id -> transactionRepository.filterByBalanceId(id) }

    val summary = Transformations.switchMap(currentBalance) { summaryInteractor.getPieChartValues(balance.value?: balanceRepository.getAll().value!![0]) }

    fun getShowLegend() : Boolean {
        return summaryInteractor.getShowLegend()
    }
}