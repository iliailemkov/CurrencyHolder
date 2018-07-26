package com.example.beardie.currencyholder.domain

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.example.beardie.currencyholder.data.CategoryRepository
import com.example.beardie.currencyholder.data.TransactionRepository
import com.example.beardie.currencyholder.data.enum.TypeCategoryEnum
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import javax.inject.Inject

class SummaryInteractor @Inject constructor(
        private val transactionRepository: TransactionRepository,
        private val categoryRepository: CategoryRepository){

    fun getPieChartValues() : LiveData<PieDataSet> {
        val liveData = MutableLiveData<PieDataSet>()
        val entries = ArrayList<PieEntry>()
        val color = ArrayList<Int>()
        var sum = 0f
        var div = 0f
        categoryRepository.getCategories().forEach { category ->
            if(category.type == TypeCategoryEnum.OUTGO) {
                transactionRepository.getTransactions().filter { el -> el.category == category }.forEach { t ->
                    div += Math.abs(t.count.toFloat())
                }
            }
        }
        categoryRepository.getCategories().forEach { category ->
            if(category.type == TypeCategoryEnum.OUTGO) {

                transactionRepository.getTransactions().filter { el -> el.category == category }.forEach { t ->
                    sum += Math.abs(t.count.toFloat())
                }
                if(sum / div > 0.05f) {
                    entries.add(PieEntry(sum / div, category.name))
                }
                sum = 0f
            }
        }
        liveData.value = PieDataSet(entries, "Категории")
        liveData.value!!.colors = color
        return liveData
    }
}