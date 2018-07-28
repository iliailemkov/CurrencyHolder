package com.example.beardie.currencyholder.di.module

import android.arch.lifecycle.ViewModel
import com.example.beardie.currencyholder.di.ViewModelKey
import com.example.beardie.currencyholder.viewmodel.FinanceViewModel
import com.example.beardie.currencyholder.viewmodel.SettingsViewModel
import com.example.beardie.currencyholder.viewmodel.TransactionViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(FinanceViewModel::class)
    abstract fun bindFinanceViewModel(viewModel : FinanceViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SettingsViewModel::class)
    abstract fun bindSettingsViewModel(view : SettingsViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TransactionViewModel::class)
    abstract fun bindTransactionViewModel(view : TransactionViewModel) : ViewModel
}