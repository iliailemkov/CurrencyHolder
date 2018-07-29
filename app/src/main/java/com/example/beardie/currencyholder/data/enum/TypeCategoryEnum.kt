package com.example.beardie.currencyholder.data.enum

import android.text.TextUtils

enum class TypeCategoryEnum constructor(
        val title: String,
        val res: Int) {
    INCOME("Доходы", 0),
    OUTGO("Расходы", 1);

    companion object {
        fun findByTitle(value: String): TypeCategoryEnum? {
            if (TextUtils.isEmpty(value))
                return null
            for (item in TypeCategoryEnum.values())
                if (item.title.equals(value, ignoreCase = true))
                    return item
            return null
        }

        fun findByNumber(value : Int): TypeCategoryEnum? {
            for (item in TypeCategoryEnum.values())
                if (item.res == value)
                    return item
            return null
        }
    }
}