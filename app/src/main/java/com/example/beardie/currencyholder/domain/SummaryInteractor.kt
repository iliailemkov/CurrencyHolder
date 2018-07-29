package com.example.beardie.currencyholder.domain

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.example.beardie.currencyholder.data.CategoryRepository
import com.example.beardie.currencyholder.data.TransactionRepository
import com.example.beardie.currencyholder.data.enum.TypeCategoryEnum
import com.example.beardie.currencyholder.data.model.Balance
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import javax.inject.Inject

class SummaryInteractor @Inject constructor(
        private val transactionRepository: TransactionRepository,
        private val categoryRepository: CategoryRepository){

    fun getPieChartValues(balance: Balance) : LiveData<PieDataSet> {
        val liveData = MutableLiveData<PieDataSet>()
        val entries = ArrayList<PieEntry>()
        val color = ArrayList<Int>()
        var sum = 0f
        categoryRepository.getAll().value?.forEach { category ->
            if(category.type == TypeCategoryEnum.OUTGO) {
                transactionRepository.getAll().value?.filter { el -> (el.balance == balance) and (el.category == category) }?.forEach { t ->
                    sum += Math.abs(t.count.toFloat())
                }
                if(sum > 0)
                entries.add(PieEntry(sum, category.name))
                sum = 0f
            }
        }
        liveData.value = PieDataSet(entries, "")
        liveData.value!!.colors = color
        return liveData
    }
}