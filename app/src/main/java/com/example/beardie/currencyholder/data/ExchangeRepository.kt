package com.example.beardie.currencyholder.data

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.example.beardie.currencyholder.data.api.ExchangeApiService
import com.example.beardie.currencyholder.data.model.ExchangeCurrency
import com.example.beardie.currencyholder.data.model.FinanceCurrency
import okhttp3.ResponseBody
import retrofit2.Callback
import javax.inject.Inject


class ExchangeRepository @Inject constructor() {

    fun getAll() : LiveData<List<ExchangeCurrency>> {
        val exchangeCurrency = MutableLiveData<List<ExchangeCurrency>>()
        exchangeCurrency.value = HardcodeValues.exchange
        return exchangeCurrency
    }

    fun getExchangeRate(from: FinanceCurrency, to: FinanceCurrency, callback: Callback<ResponseBody> ) {
        ExchangeApiService.create()
                .getExchangeRate(from.shortTitle + "_" + to.shortTitle, "y")
                .enqueue(callback)
    }
}