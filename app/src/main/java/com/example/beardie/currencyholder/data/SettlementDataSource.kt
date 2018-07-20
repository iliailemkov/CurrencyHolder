package com.example.beardie.currencyholder.data

import com.example.beardie.currencyholder.data.model.Settlement

interface SettlementDataSource {

    fun getSettlements(): List<Settlement>

    fun getSettlement(settlementId: String): Settlement?

    fun refreshSettlement()
}