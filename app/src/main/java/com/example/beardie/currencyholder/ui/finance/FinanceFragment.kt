package com.example.beardie.currencyholder.ui.finance

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.beardie.currencyholder.R
import com.example.beardie.currencyholder.data.model.Balance
import com.example.beardie.currencyholder.data.model.FinanceCurrency
import com.example.beardie.currencyholder.data.model.Transaction
import com.example.beardie.currencyholder.di.ViewModelFactory
import com.example.beardie.currencyholder.viewmodel.FinanceViewModel
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_finance.*
import javax.inject.Inject


class FinanceFragment : DaggerFragment(),
        AdapterView.OnItemSelectedListener,
        AppBarLayout.OnOffsetChangedListener {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var financeViewModel : FinanceViewModel

    companion object {
        fun newInstance(): FinanceFragment {
            return FinanceFragment()
        }
    }

    @SuppressLint("RestrictedApi")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_finance, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        financeViewModel = ViewModelProviders.of(this, viewModelFactory).get(FinanceViewModel::class.java)
        appbar.addOnOffsetChangedListener(this)
        initBalance()
        initTransactionList()
        initChart()
    }

    override fun onStop() {
        super.onStop()
        financeViewModel.getBalance().removeObservers(this)
        financeViewModel.getCurrencyShortNameList().removeObservers(this)
        financeViewModel.getTransactions().removeObservers(this)
        financeViewModel.getSummary().removeObservers(this)
    }

    private fun initBalance() {
        val changeBalance = Observer<Balance> { res ->
            if (res !== null) {
                if (res.balance < 0) {
                    tv_balance.setTextColor(Color.RED)
                } else {
                    tv_balance.setTextColor(Color.GREEN)
                }
                tv_balance.text = String.format(getString(R.string.format_balance_text,
                        res.balance,
                        res.currency.symbol))
            }
        }
        financeViewModel.getBalance().observe(this, changeBalance)
        val currencyNameList: Observer<List<FinanceCurrency>> = Observer { res ->
            if(res != null) {
                s_currency_list.adapter = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, res.map { l -> l.shortTitle })
            }
        }
        financeViewModel.getCurrencyShortNameList().observe(this, currencyNameList)
        //financeViewModel.currentCurrency = shared.getInt(SharedConstants.DEFAULT_CURRENCY, 0)
        s_currency_list.onItemSelectedListener = this
    }

    private fun initTransactionList() {
        val changeTransaction: Observer<List<Transaction>> = Observer { res ->
            if (res != null) {
                rv_transaction_list.layoutManager = LinearLayoutManager(context)
                rv_transaction_list.adapter = TransactionAdapter(context!!, res)
                if (financeViewModel.getTransactions().value?.isNotEmpty() == true) {
                    rv_transaction_list.visibility = View.VISIBLE
                    view_holder.visibility = View.GONE
                } else {
                    rv_transaction_list.visibility = View.GONE
                    view_holder.visibility = View.VISIBLE
                }
            }
        }
        financeViewModel.getTransactions().observe(this, changeTransaction)
        fab_add_transaction.setOnClickListener { view ->
            Toast.makeText(context, "Добавлена группа", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initChart() {
        val dataSet : Observer<PieDataSet> = Observer { res ->
            if(res != null) {
                val dataSet = financeViewModel.getSummary().value
                dataSet!!.sliceSpace = 8f
                dataSet.selectionShift = 8f
                dataSet.colors = ColorTemplate.MATERIAL_COLORS.toList()
                dataSet.setDrawValues(false)
                val data = PieData(dataSet)
                data.setValueFormatter(PercentFormatter())
                data.setValueTextSize(12f)
                data.setValueTextColor(Color.BLACK)
                chart.data = data
                chart.isHighlightPerTapEnabled = true
                chart.highlightValues(null)
                chart.invalidate()
            }
        }
        financeViewModel.getSummary().observe(this, dataSet)

        chart.legend.isEnabled = true
        chart.legend.orientation = Legend.LegendOrientation.VERTICAL
        chart.legend.horizontalAlignment = Legend.LegendHorizontalAlignment.LEFT
        chart.legend.verticalAlignment = Legend.LegendVerticalAlignment.CENTER
        chart.description.isEnabled = false

        chart.isDrawHoleEnabled = true
        chart.holeRadius = 45f
        chart.transparentCircleRadius = 0f
        chart.setHoleColor(Color.WHITE)
        chart.setTransparentCircleColor(Color.WHITE)
        chart.setTransparentCircleAlpha(50)

        chart.setDrawCenterText(true)
        chart.setUsePercentValues(true)
        chart.setExtraOffsets(8f, 8f, 8f, 8f)
        chart.dragDecelerationFrictionCoef = 0.95f
        chart.rotationAngle = 0f
        chart.isRotationEnabled = true
        chart.isHighlightPerTapEnabled = true
        chart.animateY(1000, Easing.EasingOption.EaseInOutQuad)
        chart.setEntryLabelColor(Color.BLACK)
        chart.setEntryLabelTextSize(14f)
    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
        if (verticalOffset == 0) {
            (activity as AppCompatActivity).supportActionBar?.elevation = 0f
        } else if(verticalOffset == -appBarLayout.totalScrollRange) {
            (activity as AppCompatActivity).supportActionBar?.elevation = 0f
            rl_balance_view.elevation = 4f
        } else {
            rl_balance_view.elevation = 0f
            (activity as AppCompatActivity).supportActionBar?.elevation = 4f
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
        financeViewModel.currentCurrency = pos
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }
}
