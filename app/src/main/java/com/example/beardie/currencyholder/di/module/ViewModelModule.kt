package com.example.beardie.currencyholder.di.module

import android.arch.lifecycle.ViewModel
import com.example.beardie.currencyholder.di.ViewModelKey
import com.example.beardie.currencyholder.viewmodel.FinanceViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(FinanceViewModel::class)
    abstract fun bindFinanceViewModel(viewModel : FinanceViewModel) : ViewModel

}