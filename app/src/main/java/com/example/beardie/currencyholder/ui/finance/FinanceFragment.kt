package com.example.beardie.currencyholder.ui.finance

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.beardie.currencyholder.R
import com.example.beardie.currencyholder.SharedConstants
import com.example.beardie.currencyholder.data.model.Balance
import com.example.beardie.currencyholder.data.model.FinanceCurrency
import com.example.beardie.currencyholder.data.model.Transaction
import com.example.beardie.currencyholder.di.ViewModelFactory
import com.example.beardie.currencyholder.viewmodel.FinanceViewModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.activity_finance.*
import kotlinx.android.synthetic.main.content_finance.*
import kotlinx.android.synthetic.main.fragment_finance.*
import javax.inject.Inject

class FinanceFragment @Inject constructor() : DaggerFragment(), AdapterView.OnItemSelectedListener {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var shared : SharedPreferences

    private lateinit var financeViewModel : FinanceViewModel

    @SuppressLint("RestrictedApi")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val baseActivity = activity as FinanceActivity
        baseActivity.toolbar.setTitle(R.string.finance_toolbar_title)
        baseActivity.toolbar.elevation = 0f
        baseActivity.nv_left_menu.setNavigationItemSelectedListener(baseActivity)
        baseActivity.toggle = ActionBarDrawerToggle(baseActivity, baseActivity.dl_view, baseActivity.toolbar, R.string.app_name, R.string.app_name)
        baseActivity.toggle.syncState()
        baseActivity.dl_view.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        //baseActivity.supportActionBar?.setDefaultDisplayHomeAsUpEnabled(false)

        return inflater.inflate(R.layout.fragment_finance, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        s_currency_list.onItemSelectedListener = this
        financeViewModel = ViewModelProviders.of(this, viewModelFactory).get(FinanceViewModel::class.java)
        generateObservers()
    }

    private fun generateObservers() {
        val changeBalance: Observer<Balance> = Observer { res ->
            if(res !== null) {
                if(res.balance < 0)
                    tv_balance.setTextColor(Color.RED)
                else
                    tv_balance.setTextColor(Color.GREEN)
                tv_balance.text = String.format(getString(R.string.format_balance_text,
                        res.balance,
                        res.currency.symbol))
            }
        }

        val changeTransaction: Observer<List<Transaction>> = Observer { res ->
            if(res != null) {
                rv_transaction_list.layoutManager = LinearLayoutManager(context)
                rv_transaction_list.adapter = TransactionAdapter(context, res)
                if (financeViewModel.getTransactions().value?.isNotEmpty() == true) {
                    rv_transaction_list.visibility = View.VISIBLE
                    view_holder.visibility = View.GONE
                } else {
                    rv_transaction_list.visibility = View.GONE
                    view_holder.visibility = View.VISIBLE
                }
            }
        }

        val currencyNameList: Observer<List<FinanceCurrency>> = Observer { res ->
            if(res != null) {
                s_currency_list.adapter = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, res.map { l -> l.shortTitle })
            }
        }

        financeViewModel.getTransactions().observe(this, changeTransaction)
        financeViewModel.getBalance().observe(this, changeBalance)
        financeViewModel.getCurrencyShortNameList().observe(this, currencyNameList)

        financeViewModel.currentCurrency = shared.getInt(SharedConstants.DEFAULT_CURRENCY, 0)
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
        financeViewModel.currentCurrency = pos
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }
}
