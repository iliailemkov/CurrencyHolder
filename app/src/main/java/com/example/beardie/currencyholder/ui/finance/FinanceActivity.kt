package com.example.beardie.currencyholder.ui.finance

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.beardie.currencyholder.R
import com.example.beardie.currencyholder.viewmodel.FinanceViewModel
import javax.inject.Inject

class FinanceActivity : AppCompatActivity() {

    @Inject
    lateinit var financeViewModel : FinanceViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finance)
    }
}
