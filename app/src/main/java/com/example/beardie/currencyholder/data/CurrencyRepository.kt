package com.example.beardie.currencyholder.data

import com.example.beardie.currencyholder.data.model.FinanceCurrency
import javax.inject.Inject

class CurrencyRepository @Inject constructor() {
    fun getCurrencyList() : List<FinanceCurrency> {
        return HardcodeValues.currency
    }
}