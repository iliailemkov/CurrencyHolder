package com.example.beardie.currencyholder.data

import com.example.beardie.currencyholder.data.enum.CurrencyEnum
import com.example.beardie.currencyholder.data.enum.TypeOperationEnum
import com.example.beardie.currencyholder.data.model.Settlement
import java.util.*

class SettlementRepositroy : SettlementDataSource {

    //hardcode values
    private val settlements = listOf(
        Settlement("1", TypeOperationEnum.SUM, 100, CurrencyEnum.RUB),
        Settlement("2", TypeOperationEnum.SUM, -90, CurrencyEnum.RUB),
        Settlement("3", TypeOperationEnum.SUM, 99, CurrencyEnum.RUB),
        Settlement("4", TypeOperationEnum.SUM, -133, CurrencyEnum.RUB)
    )

    override fun getSettlements() : List<Settlement> {
        return settlements
    }

    override fun getSettlement(settlementId: String): Settlement? {
        return settlements.find { e -> e.id == settlementId }
    }

    override fun refreshSettlement() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}