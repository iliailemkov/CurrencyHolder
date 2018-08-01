package com.example.beardie.currencyholder.ui.finance

import android.app.AlertDialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.beardie.currencyholder.R
import com.example.beardie.currencyholder.data.enum.TypeCategoryEnum
import com.example.beardie.currencyholder.data.model.Balance
import com.example.beardie.currencyholder.data.model.FinanceCurrency
import com.example.beardie.currencyholder.data.model.TransactionCategory
import com.example.beardie.currencyholder.di.ViewModelFactory
import com.example.beardie.currencyholder.ui.Navigator
import com.example.beardie.currencyholder.viewmodel.TransactionViewModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_add_transaction.*
import java.lang.IllegalArgumentException
import java.util.*
import javax.inject.Inject


class AddTransactionFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var transactionViewModel: TransactionViewModel

    private var dateTime = Calendar.getInstance()

    private val categoryList: Observer<List<TransactionCategory>> = Observer { res ->
        if(res != null) {
            s_category.adapter = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, res.map { l -> l.name })
        }
    }

    private val balanceList: Observer<List<Balance>> = Observer { res ->
        if(res != null) {
            s_balance.adapter = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, res.map { l -> l.name })
        }
    }

    private val currencyList: Observer<List<FinanceCurrency>> = Observer { res ->
        if(res != null) {
            s_currency.adapter = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, res.map { l -> l.shortTitle })
        }
    }

    companion object {
        fun newInstance(): AddTransactionFragment {
            return AddTransactionFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_add_transaction, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        transactionViewModel = ViewModelProviders.of(this, viewModelFactory).get(TransactionViewModel::class.java)

        s_category_type.adapter = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, TypeCategoryEnum.values().toList().map { v -> getString(v.stringRes) })
        s_category_type.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>, selectedItemView: View, position: Int, id: Long) {
                transactionViewModel.filter.value = position
            }

            override fun onNothingSelected(parentView: AdapterView<*>) {
            }
        }
        initSaveButton()
    }

    override fun onStart() {
        super.onStart()
        transactionViewModel.balances.observe(this, balanceList)
        transactionViewModel.categories.observe(this, categoryList)
        transactionViewModel.currency.observe(this, currencyList)
    }

    override fun onStop() {
        super.onStop()
        transactionViewModel.categories.removeObservers(this)
        transactionViewModel.balances.removeObservers(this)
        transactionViewModel.currency.removeObservers(this)
    }

    private fun initSaveButton() {
        btn_save.setOnClickListener {
            if( transactionViewModel.balances.value?.find { l -> l.name == s_balance.adapter.getItem(s_balance.selectedItemPosition) }!!.currency.shortTitle != s_currency.adapter.getItem(s_currency.selectedItemPosition)) {
                AlertDialog.Builder(context).setMessage(R.string.convert_message)
                        .setCancelable(true)
                        .setPositiveButton("OK") { dialogInterface, i ->
                            try {
                                saveTransaction()
                            } catch (e: RuntimeException) {
                                Toast.makeText(activity, R.string.exchange_response_exception, Toast.LENGTH_SHORT).show()
                            }
                        }
                        .create()
                        .show()
            } else {
                try {
                    saveTransaction()
                } catch (e: IllegalArgumentException) {
                    Toast.makeText(activity, R.string.values_validate_toast, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun saveTransaction(){
        transactionViewModel.addTransaction(et_amount.text.toString().toDouble(),
                transactionViewModel.balances.value?.find { l -> l.name == s_balance.adapter.getItem(s_balance.selectedItemPosition) }!!,
                transactionViewModel.currency.value?.find { c -> c.shortTitle == s_currency.adapter.getItem(s_currency.selectedItemPosition) }!!,
                dateTime.time,
                transactionViewModel.categories.value?.find { c -> c.name == s_category.adapter.getItem(s_category.selectedItemPosition) }!!)
        (activity!! as Navigator).navigateBack()
    }
}
