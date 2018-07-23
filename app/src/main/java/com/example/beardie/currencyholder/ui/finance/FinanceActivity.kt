package com.example.beardie.currencyholder.ui.finance

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v7.app.ActionBarDrawerToggle
import android.view.Gravity
import android.view.MenuItem
import com.example.beardie.currencyholder.R
import com.example.beardie.currencyholder.ui.about.AboutFragment
import com.example.beardie.currencyholder.ui.settings.SettingsFragment
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_finance.*
import kotlinx.android.synthetic.main.content_finance.*
import javax.inject.Inject

class FinanceActivity : DaggerAppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    @Inject lateinit var financeFragment: FinanceFragment
    @Inject lateinit var settingsFragment: SettingsFragment
    @Inject lateinit var aboutFragment: AboutFragment
    lateinit var toggle : ActionBarDrawerToggle

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finance)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        nv_left_menu.setNavigationItemSelectedListener(this)
        toggle = ActionBarDrawerToggle(this, dl_view, toolbar, R.string.app_name, R.string.app_name)
        toggle.syncState()
        if(savedInstanceState == null)
            supportFragmentManager.beginTransaction().add(R.id.fl_finance_frame, financeFragment).commit()
    }

    @SuppressLint("RestrictedApi")
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_settings -> {
                supportFragmentManager.beginTransaction().replace(R.id.fl_finance_frame, settingsFragment).addToBackStack(null).commit()
            }
            R.id.action_about -> {
                supportFragmentManager.beginTransaction().replace(R.id.fl_finance_frame, aboutFragment).addToBackStack(null).commit()
            }
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return true
    }

    override fun onBackPressed() {
        if(dl_view != null && dl_view.isDrawerOpen(Gravity.START))
            dl_view.closeDrawer(Gravity.START, true)
        else
            super.onBackPressed()
    }

}
