package com.example.beardie.currencyholder.data

import android.content.SharedPreferences
import javax.inject.Inject

class SharedPrefRepository @Inject constructor(var shared : SharedPreferences) {

    companion object {
        const val DEFAULT_CURRENCY = "DefaultCurrency"
    }

    fun getDefaultCurrency() : Int {
        return shared.getInt(DEFAULT_CURRENCY, 0)
    }
}