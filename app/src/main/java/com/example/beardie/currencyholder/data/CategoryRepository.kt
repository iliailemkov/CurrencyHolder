package com.example.beardie.currencyholder.data

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.example.beardie.currencyholder.data.enum.TypeCategoryEnum
import com.example.beardie.currencyholder.data.model.FinanceCurrency
import com.example.beardie.currencyholder.data.model.Transaction
import com.example.beardie.currencyholder.data.model.TransactionCategory
import javax.inject.Inject

class CategoryRepository @Inject constructor() {

    fun getAll() : LiveData<List<TransactionCategory>> {
        val categories = MutableLiveData<List<TransactionCategory>>()
        categories.value = HardcodeValues.category
        return categories
    }

    fun filterByType(type: Int) : LiveData<List<TransactionCategory>> {
        val categories = MutableLiveData<List<TransactionCategory>>()
        categories.value = HardcodeValues.category.filter { c -> c.type.res == type }
        return categories
    }

    fun add(category : TransactionCategory) : Unit {
        HardcodeValues.category.add(category)
    }
}