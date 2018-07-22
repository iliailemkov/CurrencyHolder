package com.example.beardie.currencyholder.ui.finance

import android.os.Bundle
import com.example.beardie.currencyholder.R
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.content_finance.*
import javax.inject.Inject

class FinanceActivity : DaggerAppCompatActivity() {

    @Inject lateinit var financeFragment: FinanceFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finance)
        setSupportActionBar(toolbar)
        //dl_view.add
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportFragmentManager.beginTransaction().add(R.id.fl_finance_frame, financeFragment).commit()
    }


}
