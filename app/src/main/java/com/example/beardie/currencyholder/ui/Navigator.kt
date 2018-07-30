package com.example.beardie.currencyholder.ui

import android.support.annotation.StringRes
import android.support.v4.app.Fragment

interface Navigator {

    fun initToolbar(@StringRes title: Int, elevation: Float)
    fun navigateTo(fragment: Fragment, transaction: String?)
    fun navigateBack()

}