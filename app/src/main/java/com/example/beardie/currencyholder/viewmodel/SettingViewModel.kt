package com.example.beardie.currencyholder.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import com.example.beardie.currencyholder.data.SettlementRepositroy

class SettingViewModel(
        context: Application,
        private val settlementRepositroy: SettlementRepositroy
) : AndroidViewModel(context) {


}