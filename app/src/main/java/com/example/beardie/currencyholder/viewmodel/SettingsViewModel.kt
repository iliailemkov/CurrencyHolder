package com.example.beardie.currencyholder.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import com.example.beardie.currencyholder.data.SharedPrefRepository
import javax.inject.Inject

class SettingsViewModel @Inject constructor(
        context: Application,
        private val sprefRepository: SharedPrefRepository
) : AndroidViewModel(context) {

    fun setSummaryBoolean(value: Boolean) {
        sprefRepository.setOnlyOutcomes(value)
    }

    fun getSummaryBoolean() : Boolean {
        return sprefRepository.getOnlyOutcomes()
    }

    fun setShowLegend(value: Boolean) {
        sprefRepository.setShowlegend(value)
    }

    fun getShowLegend() : Boolean {
        return sprefRepository.getShowlegend()
    }
}