package com.example.beardie.currencyholder.ui.finance

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import com.example.beardie.currencyholder.R
import com.example.beardie.currencyholder.data.model.FinanceCurrency
import com.example.beardie.currencyholder.data.model.TransactionCategory
import com.example.beardie.currencyholder.di.ViewModelFactory
import com.example.beardie.currencyholder.viewmodel.TransactionViewModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_add_transaction.*
import java.lang.IllegalArgumentException
import java.util.*
import javax.inject.Inject

class AddTransactionFragment : DaggerFragment(),
        DatePickerDialog.OnDateSetListener,
        TimePickerDialog.OnTimeSetListener {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var transactionViewModel: TransactionViewModel

    private var dateTime = Calendar.getInstance()


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
        initDateTimePicker()
        initCurrencyNameList()
        initCategoryList()
        initSaveButton()
    }


    private fun initDateTimePicker() {
        et_date.setOnClickListener {
            DatePickerDialog(activity, this, dateTime.get(Calendar.YEAR), dateTime.get(Calendar.MONTH), dateTime.get(Calendar.DAY_OF_MONTH)).show()
        }
    }

    private fun initCurrencyNameList() {
        val currencyNameList: Observer<List<FinanceCurrency>> = Observer { res ->
            if(res != null) {
                s_currency.adapter = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, res.map { l -> l.shortTitle })
            }
        }
        transactionViewModel.getCurrencyShortNameList().observe(this, currencyNameList)
    }

    private fun initCategoryList() {
        val categoryList: Observer<List<TransactionCategory>> = Observer { res ->
            if(res != null) {
                s_category.adapter = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, res.map { l -> l.name })
            }
        }
        transactionViewModel.getCategories().observe(this, categoryList)
    }

    private fun initSaveButton() {
        btn_save.setOnClickListener {
            try {
                transactionViewModel.addTransaction(et_amount.text.toString().toDouble(),
                        transactionViewModel.getBalance().value!!,
                        transactionViewModel.getCurrencyShortNameList().value!![transactionViewModel.currentCurrency],
                        dateTime.time,
                        transactionViewModel.getCategories().value!![4])
                activity!!.supportFragmentManager.popBackStack()
            } catch (e: IllegalArgumentException) {
                Toast.makeText(activity, R.string.values_validate_toast, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDateSet(view: DatePicker?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        dateTime.set(Calendar.YEAR, year)
        dateTime.set(Calendar.MONTH, monthOfYear)
        dateTime.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        TimePickerDialog(activity, this, dateTime.get(Calendar.HOUR_OF_DAY), dateTime.get(Calendar.MINUTE), true).show()
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        dateTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
        dateTime.set(Calendar.MINUTE, minute)
        et_date.setText(DateUtils.formatDateTime(activity, dateTime.timeInMillis, DateUtils.FORMAT_SHOW_DATE or DateUtils.FORMAT_SHOW_DATE))
    }
}
