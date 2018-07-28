package com.example.beardie.currencyholder.ui.settings

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.beardie.currencyholder.R
import com.example.beardie.currencyholder.data.model.FinanceCurrency
import com.example.beardie.currencyholder.di.ViewModelFactory
import com.example.beardie.currencyholder.viewmodel.SettingsViewModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_settings.*
import javax.inject.Inject

class SettingsFragment : DaggerFragment(), AdapterView.OnItemSelectedListener {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var settingViewModel : SettingsViewModel

    companion object {
        fun newInstance() : SettingsFragment {
            return SettingsFragment()
        }
    }

    @SuppressLint("RestrictedApi")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        settingViewModel = ViewModelProviders.of(this, viewModelFactory).get(SettingsViewModel::class.java)
        initCurrencyNameList()
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        s_currency_setting_list.onItemSelectedListener = this
    }

    override fun onStop() {
        super.onStop()
        settingViewModel.getCurrencyShortNameList().removeObservers(this)
    }

    private fun initCurrencyNameList() {
        val currencyNameList: Observer<List<FinanceCurrency>> = Observer { res ->
            if(res != null) {
                s_currency_setting_list.adapter = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, res.map { l -> l.shortTitle })
            }
        }
        settingViewModel.getCurrencyShortNameList().observe(this, currencyNameList)
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
    }

}
