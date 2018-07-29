package com.example.beardie.currencyholder.data

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.example.beardie.currencyholder.data.model.ExchangeCurrency
import javax.inject.Inject

class ExchangeRepository @Inject constructor() {

    fun getAll() : LiveData<List<ExchangeCurrency>> {
        val exchangeCurrency = MutableLiveData<List<ExchangeCurrency>>()
        exchangeCurrency.value = HardcodeValues.exchange
        return exchangeCurrency
    }
}