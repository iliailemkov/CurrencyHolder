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
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import com.example.beardie.currencyholder.R
import com.example.beardie.currencyholder.data.enum.TypeCategoryEnum
import com.example.beardie.currencyholder.data.model.Balance
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

    private val balanceList: Observer<List<Balance>> = Observer { res ->
        if(res != null) {
            s_category.adapter = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, res.map { l -> l.name })
        }
    }

    private val currencyNameList: Observer<List<FinanceCurrency>> = Observer { res ->
        if(res != null) {
            s_currency.adapter = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, res.map { l -> l.shortTitle })
        }
    }

    private val categoryList: Observer<List<TransactionCategory>> = Observer { res ->
        if(res != null) {
            s_category.adapter = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, res.map { l -> l.name })
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
        initDateTimePicker()
        initCategoryType()
        initSaveButton()
    }

    override fun onStart() {
        super.onStart()
        transactionViewModel.balances.observe(this, balanceList)
        transactionViewModel.currencyList.observe(this, currencyNameList)
        transactionViewModel.categories.observe(this, categoryList)
    }

    override fun onStop() {
        super.onStop()
        transactionViewModel.balances.removeObservers(this)
        transactionViewModel.currencyList.removeObservers(this)
        transactionViewModel.categories.removeObservers(this)
    }

    private fun initDateTimePicker() {
        et_date.setOnClickListener {
            DatePickerDialog(activity, this, dateTime.get(Calendar.YEAR), dateTime.get(Calendar.MONTH), dateTime.get(Calendar.DAY_OF_MONTH)).show()
        }
    }

    private fun initCategoryType() {
        s_category.adapter = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, TypeCategoryEnum.values())
        s_category.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>, selectedItemView: View, position: Int, id: Long) {
                transactionViewModel.filter.value = position
            }

            override fun onNothingSelected(parentView: AdapterView<*>) {
            }
        }
    }

    private fun initSaveButton() {
        btn_save.setOnClickListener {
            try {
                transactionViewModel.addTransaction(et_amount.text.toString().toDouble(),
                        transactionViewModel.balances.value!![s_balance.selectedItemPosition],
                        transactionViewModel.currencyList.value!![s_currency.selectedItemPosition],
                        dateTime.time,
                        transactionViewModel.categories.value!![s_category.selectedItemPosition])
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
        et_date.setText(DateUtils.formatDateTime(activity, dateTime.timeInMillis, DateUtils.FORMAT_SHOW_DATE or DateUtils.FORMAT_SHOW_TIME))
    }
}
