package com.example.beardie.currencyholder.ui.finance

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.beardie.currencyholder.R
import com.example.beardie.currencyholder.data.model.Balance
import com.example.beardie.currencyholder.data.model.FinanceCurrency
import com.example.beardie.currencyholder.data.model.Transaction
import com.example.beardie.currencyholder.di.ViewModelFactory
import com.example.beardie.currencyholder.viewmodel.FinanceViewModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_finance.*
import javax.inject.Inject

class FinanceFragment @Inject constructor() : DaggerFragment(), AdapterView.OnItemSelectedListener {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var financeViewModel : FinanceViewModel

    private val changeBalance: Observer<Balance> = Observer { res ->
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

    private val changeTransaction: Observer<List<Transaction>> = Observer { res ->
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

    private val currencyNameList: Observer<List<FinanceCurrency>> = Observer { res ->
        if(res != null) {
             s_currency_list.adapter = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, res.map { l -> l.shortTitle })
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        financeViewModel = ViewModelProviders.of(this, viewModelFactory).get(FinanceViewModel::class.java)
        return inflater.inflate(R.layout.fragment_finance, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        s_currency_list.onItemSelectedListener = this
        financeViewModel.getTransactions().observe(this, changeTransaction)
        financeViewModel.getBalance().observe(this, changeBalance)
        financeViewModel.getCurrencyShortNameList().observe(this, currencyNameList)
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
        financeViewModel.currentCurrency = pos
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }
}
