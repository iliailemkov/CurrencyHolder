package com.example.beardie.currencyholder.di.module

import com.example.beardie.currencyholder.ui.about.AboutFragment
import com.example.beardie.currencyholder.ui.finance.AddTransactionFragment
import com.example.beardie.currencyholder.ui.finance.FinanceActivity
import com.example.beardie.currencyholder.ui.finance.FinanceFragment
import com.example.beardie.currencyholder.ui.settings.SettingsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ViewModule {

    @ContributesAndroidInjector
    abstract fun financeActivity() : FinanceActivity

    @ContributesAndroidInjector
    abstract fun financeFragment() : FinanceFragment

    @ContributesAndroidInjector
    abstract fun settingsFragment() : SettingsFragment

    @ContributesAndroidInjector
    abstract fun aboutFragment() : AboutFragment

    @ContributesAndroidInjector
    abstract fun addTransactionFragment() : AddTransactionFragment
}