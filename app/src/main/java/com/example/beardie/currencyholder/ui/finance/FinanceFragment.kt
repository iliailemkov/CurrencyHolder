package com.example.beardie.currencyholder.ui.finance

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.DividerItemDecoration.VERTICAL
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.beardie.currencyholder.R
import com.example.beardie.currencyholder.data.model.Balance
import com.example.beardie.currencyholder.data.model.Transaction
import com.example.beardie.currencyholder.di.ViewModelFactory
import com.example.beardie.currencyholder.ui.Navigator
import com.example.beardie.currencyholder.viewmodel.FinanceViewModel
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.github.mikephil.charting.utils.ColorTemplate
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_finance.*
import javax.inject.Inject


class FinanceFragment : DaggerFragment(),
        AdapterView.OnItemSelectedListener,
        AppBarLayout.OnOffsetChangedListener,
        OnChartValueSelectedListener{

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var financeViewModel : FinanceViewModel

    private lateinit var transactionAdapter : TransactionAdapter

    private val changeBalance = Observer<Balance> { res ->
        if (res !== null) {
            if (res.balance < 0) {
                tv_balance.setTextColor(ContextCompat.getColor(context!!, android.R.color.holo_red_dark))
            } else {
                tv_balance.setTextColor(ContextCompat.getColor(context!!, android.R.color.holo_green_dark))
            }
            tv_balance.text = String.format(getString(R.string.format_balance_text,
                    res.balance,
                    res.currency.symbol))
        }
    }

    private val changeTransaction: Observer<List<Transaction>> = Observer { res ->
        if (res != null) {
            if (financeViewModel.transactions.value?.isNotEmpty() == true) {
                rv_transaction_list.visibility = View.VISIBLE
                view_holder.visibility = View.GONE
            } else {
                rv_transaction_list.visibility = View.GONE
                view_holder.visibility = View.VISIBLE
            }
        }
        rv_transaction_list.adapter = transactionAdapter
        transactionAdapter.transactions = res?: emptyList()
        transactionAdapter.notifyDataSetChanged()
    }

    private val dataSet : Observer<PieDataSet> = Observer { res ->
        if(res != null) {
            val dataSet = financeViewModel.summary.value
            dataSet!!.sliceSpace = 8f
            dataSet.selectionShift = 8f
            dataSet.colors = ColorTemplate.MATERIAL_COLORS.toList()
            dataSet.setDrawValues(true)
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

    companion object {
        fun newInstance(): FinanceFragment {
            return FinanceFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_finance, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        financeViewModel = ViewModelProviders.of(this, viewModelFactory).get(FinanceViewModel::class.java)
        appbar.addOnOffsetChangedListener(this)

        s_balance_names.adapter = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, financeViewModel.balances.value!!.map { r -> r.name })
        s_balance_names.onItemSelectedListener = this

        fab_add_transaction.setOnClickListener { view ->
            if (activity != null) {
                (activity as Navigator).initToolbar(R.string.add_transaction_toolbar_title, resources.getDimension(R.dimen.default_app_elevation))
                (activity as Navigator).navigateTo(AddTransactionFragment.newInstance(), null)
            }
        }

        transactionAdapter = TransactionAdapter(context!!)
        rv_transaction_list.layoutManager = LinearLayoutManager(context)
        rv_transaction_list.addItemDecoration(DividerItemDecoration(context, VERTICAL))
        initChart()
    }

    override fun onStart() {
        super.onStart()
        financeViewModel.balance.observe(this, changeBalance)
        financeViewModel.transactions.observe(this, changeTransaction)
        financeViewModel.summary.observe(this, dataSet)
    }

    override fun onStop() {
        super.onStop()
        financeViewModel.balance.removeObservers(this)
        financeViewModel.transactions.removeObservers(this)
        financeViewModel.summary.removeObservers(this)
    }

    private fun initChart() {
        chart.legend.isEnabled = true
        chart.legend.orientation = Legend.LegendOrientation.VERTICAL
        chart.legend.horizontalAlignment = Legend.LegendHorizontalAlignment.LEFT
        chart.legend.verticalAlignment = Legend.LegendVerticalAlignment.CENTER
        chart.description.isEnabled = false

        chart.isDrawHoleEnabled = true
        chart.holeRadius = 30f
        chart.transparentCircleRadius = 40f
        chart.setHoleColor(Color.WHITE)
        chart.setTransparentCircleColor(Color.WHITE)
        chart.setTransparentCircleAlpha(90)

        chart.setUsePercentValues(true)
        chart.setExtraOffsets(8f, 8f, 8f, 8f)
        chart.dragDecelerationFrictionCoef = 0.95f
        chart.rotationAngle = 0f
        chart.isRotationEnabled = true
        chart.isHighlightPerTapEnabled = true
        chart.animateY(1000, Easing.EasingOption.EaseInOutQuad)
        chart.setEntryLabelColor(Color.BLACK)
        chart.setEntryLabelTextSize(14f)

        chart.setOnChartValueSelectedListener(this)
    }

    override fun onValueSelected(e: Entry?, h: Highlight?) {
    }

    override fun onNothingSelected() {
    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
        if (verticalOffset == 0) {
            (activity as AppCompatActivity).supportActionBar?.elevation = 0f
            rl_balance_view.elevation = 0f
        } else if(verticalOffset == -appBarLayout.totalScrollRange) {
            (activity as AppCompatActivity).supportActionBar?.elevation = 0f
            rl_balance_view.elevation = 0f
        } else {
            (activity as AppCompatActivity).supportActionBar?.elevation = resources.getDimension(R.dimen.default_app_elevation)
            rl_balance_view.elevation = resources.getDimension(R.dimen.default_app_elevation)
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
        financeViewModel.currentBalance.value = financeViewModel.balances.value!![pos].id
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }
}
