package com.example.beardie.currencyholder.data

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.example.beardie.currencyholder.data.model.FinanceCurrency
import javax.inject.Inject

class CurrencyRepository @Inject constructor() {

    fun getAll() : LiveData<List<FinanceCurrency>> {
        val currencyList = MutableLiveData<List<FinanceCurrency>>()
        currencyList.value = HardcodeValues.currency
        return currencyList
    }
}