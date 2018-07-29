package com.example.beardie.currencyholder.data

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.example.beardie.currencyholder.data.model.Balance
import com.example.beardie.currencyholder.data.model.FinanceCurrency
import javax.inject.Inject

class BalanceRepository @Inject constructor() {

    fun getAll() : LiveData<List<Balance>> {
        val balances = MutableLiveData<List<Balance>>()
        balances.value = HardcodeValues.balances
        return balances
    }

    fun findById(id : String): LiveData<Balance> {
        val balance = MutableLiveData<Balance>()
        balance.value = HardcodeValues.balances.find { b -> b.id == id }
        return balance
    }

    fun setBalance(balance: Balance, amount : Double) {
    }
}