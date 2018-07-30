package com.example.beardie.currencyholder.ui.finance

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.view.Gravity
import android.view.MenuItem
import com.example.beardie.currencyholder.R
import com.example.beardie.currencyholder.ui.Navigator
import com.example.beardie.currencyholder.ui.about.AboutFragment
import com.example.beardie.currencyholder.ui.settings.SettingsFragment
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_finance.*
import kotlinx.android.synthetic.main.content_finance.*

class FinanceActivity : DaggerAppCompatActivity(),
        NavigationView.OnNavigationItemSelectedListener,
        FragmentManager.OnBackStackChangedListener,
        Navigator {

    private lateinit var toggle : ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finance)
        setSupportActionBar(toolbar)
        supportFragmentManager.addOnBackStackChangedListener(this)
        nv_left_menu.setNavigationItemSelectedListener(this)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        toggle = ActionBarDrawerToggle(this, dl_view, toolbar, R.string.app_name, R.string.app_name)
        toggle.isDrawerIndicatorEnabled = true
        dl_view.addDrawerListener(toggle)
        toggle.syncState()
        if(savedInstanceState == null) {
            initToolbar(R.string.finance_toolbar_title, 4f)
            supportFragmentManager.beginTransaction().add(R.id.fl_finance_frame, FinanceFragment.newInstance()).commit()
        }
    }

    override fun onBackStackChanged() {
        if(supportFragmentManager.backStackEntryCount > 0) {
            toggle.isDrawerIndicatorEnabled = false
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            toolbar.setNavigationOnClickListener { onBackPressed() }
            dl_view.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        } else {
            initToolbar(R.string.finance_toolbar_title, resources.getDimension(R.dimen.default_app_elevation))
            toggle = ActionBarDrawerToggle(this, dl_view, toolbar, R.string.app_name, R.string.app_name)
            toggle.isDrawerIndicatorEnabled = true
            dl_view.addDrawerListener(toggle)
            dl_view.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            toggle.syncState()
        }
    }

    override fun initToolbar(@StringRes title : Int, elevation : Float) {
        toolbar.setTitle(title)
        toolbar.elevation = elevation
    }

    override fun navigateTo(fragment: Fragment, transaction: String?) {
        supportFragmentManager.beginTransaction().replace(R.id.fl_finance_frame, fragment).addToBackStack(transaction).commit()
    }

    override fun navigateBack() {
        supportFragmentManager.popBackStack()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_settings -> {
                initToolbar(R.string.settings_toolbar_title, resources.getDimension(R.dimen.default_app_elevation))
                navigateTo(SettingsFragment.newInstance(), null)
            }
            R.id.action_about -> {
                initToolbar(R.string.about_toolbar_title, resources.getDimension(R.dimen.default_app_elevation))
                navigateTo(AboutFragment.newInstance(), null)
            }
        }
        return true
    }

    override fun onBackPressed() {
        if(dl_view != null && dl_view.isDrawerOpen(Gravity.START)) {
            dl_view.closeDrawer(Gravity.START, true)
        }
        if(supportFragmentManager.backStackEntryCount > 0) {
            navigateBack()
        } else {
            super.onBackPressed()
        }
    }

}
