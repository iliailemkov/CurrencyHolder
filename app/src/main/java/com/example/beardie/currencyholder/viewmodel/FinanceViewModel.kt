package com.example.beardie.currencyholder.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import com.example.beardie.currencyholder.data.model.Balance
import com.example.beardie.currencyholder.data.model.Transaction
import javax.inject.Inject
import android.arch.lifecycle.LiveData
import com.example.beardie.currencyholder.data.*
import com.example.beardie.currencyholder.data.model.FinanceCurrency
import com.example.beardie.currencyholder.domain.SummaryInteractor
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet

class FinanceViewModel @Inject constructor(
        context: Application,
        private val transactionRepository: TransactionRepository,
        private val balanceRepository: BalanceRepository,
        private val currencyRepository: CurrencyRepository,
        private val summaryInteractor: SummaryInteractor,
        private val prefRepository: SharedPrefRepository
) : AndroidViewModel(context) {

    var currentBalance : String = "0"// prefRepository.getDefaultCurrency()
        set(value) {
            balance.value = balanceRepository.getBalance(value)
            transactions.value = transactionRepository.getTransactions(balance.value!!)
            summary.value = summaryInteractor.getPieChartValues(balance.value!!).value
        }

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
            balance.value = balanceRepository.getBalance(currentBalance)
        }
        balance.value = balance.value
        return balance
    }

    private var balances = MutableLiveData<List<Balance>>()

    fun getBalanceNames(): LiveData<List<Balance>> {
        if (balances.value == null) {
            balances.value = balanceRepository.getBalances()
        }
        balances.value = balances.value
        return balances
    }

    private var transactions = MutableLiveData<List<Transaction>>()

    fun getTransactions(): LiveData<List<Transaction>> {
        if (transactions.value == null) {
            transactions.value = transactionRepository.getTransactions().filter { f -> f.balance.id == currentBalance }
        }
        return transactions
    }

    private var summary = MutableLiveData<PieDataSet>()

    fun getSummary(): LiveData<PieDataSet> {
        if (summary.value == null) {
            summary.value = summaryInteractor.getPieChartValues(balance.value?: balanceRepository.getBalances()[0]).value
        }
        return summary
    }
}