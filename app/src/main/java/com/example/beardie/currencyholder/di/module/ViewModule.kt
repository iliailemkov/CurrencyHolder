package com.example.beardie.currencyholder.di.module

import com.example.beardie.currencyholder.ui.finance.FinanceActivity
import com.example.beardie.currencyholder.ui.finance.FinanceFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ViewModule {

    @ContributesAndroidInjector
    abstract fun financeActivity() : FinanceActivity

    @ContributesAndroidInjector
    abstract fun financeFragment() : FinanceFragment
}