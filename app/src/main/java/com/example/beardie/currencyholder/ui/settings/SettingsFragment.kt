package com.example.beardie.currencyholder.ui.settings

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.beardie.currencyholder.R
import com.example.beardie.currencyholder.data.model.Balance
import com.example.beardie.currencyholder.data.model.FinanceCurrency
import com.example.beardie.currencyholder.di.ViewModelFactory
import com.example.beardie.currencyholder.viewmodel.SettingsViewModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_settings.*
import javax.inject.Inject

class SettingsFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var settingViewModel : SettingsViewModel

    companion object {
        fun newInstance() : SettingsFragment {
            return SettingsFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        settingViewModel = ViewModelProviders.of(this, viewModelFactory).get(SettingsViewModel::class.java)
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        switch_summary.isChecked = settingViewModel.getSummaryBoolean()
        switch_summary.setOnCheckedChangeListener { compoundButton, b ->
            settingViewModel.setSummaryBoolean(b)
        }
        switch_legend.isChecked = settingViewModel.getShowLegend()
        switch_legend.setOnCheckedChangeListener { compoundButton, b ->
            settingViewModel.setShowLegend(b)
        }
        tv_license.setOnClickListener { view ->
            AlertDialog.Builder(context).setMessage(R.string.mit_license).setTitle("License").create().show()
        }
    }
}
