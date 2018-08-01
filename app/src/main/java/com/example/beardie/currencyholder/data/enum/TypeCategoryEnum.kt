package com.example.beardie.currencyholder.data.enum

import com.example.beardie.currencyholder.R

enum class TypeCategoryEnum constructor(
        val stringRes: Int,
        val res: Int) {
    INCOME(R.string.income_title, 0),
    OUTGO(R.string.outcome_title, 1);

    companion object {
        fun findByNumber(value : Int): TypeCategoryEnum? {
            for (item in TypeCategoryEnum.values())
                if (item.res == value)
                    return item
            return null
        }
    }
}