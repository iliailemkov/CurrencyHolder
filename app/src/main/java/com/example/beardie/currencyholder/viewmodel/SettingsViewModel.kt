package com.example.beardie.currencyholder.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.example.beardie.currencyholder.data.CurrencyRepository
import com.example.beardie.currencyholder.data.TransactionRepository
import com.example.beardie.currencyholder.data.model.FinanceCurrency
import javax.inject.Inject

class SettingsViewModel @Inject constructor(
        context: Application,
        private val currencyRepository: CurrencyRepository
) : AndroidViewModel(context) {

    private var currencyList = MutableLiveData<List<FinanceCurrency>>()

    fun getCurrencyShortNameList(): LiveData<List<FinanceCurrency>> {
        if (currencyList.value == null) {
            currencyList.value = currencyRepository.getCurrencyList()
        }
        return currencyList
    }
}