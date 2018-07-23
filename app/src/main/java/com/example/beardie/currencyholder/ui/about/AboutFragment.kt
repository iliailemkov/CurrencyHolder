package com.example.beardie.currencyholder.ui.about

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.widget.DrawerLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.beardie.currencyholder.R
import com.example.beardie.currencyholder.ui.finance.FinanceActivity
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.activity_finance.*
import kotlinx.android.synthetic.main.content_finance.*
import kotlinx.android.synthetic.main.fragment_about.*
import java.util.concurrent.ThreadLocalRandom
import javax.inject.Inject

class AboutFragment @Inject constructor(): DaggerFragment(), View.OnClickListener {

       val listSmile = listOf("(＾▽＾)",
                             "(◕‿◕)",
                             "(✯◡✯)",
                             "＼(＾▽＾)／",
                             "(＠＾◡＾)")

       @SuppressLint("RestrictedApi")
       override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                                 savedInstanceState: Bundle?): View? {
              val baseActivity = activity as FinanceActivity
              baseActivity.toolbar.setTitle(R.string.about_toolbar_title)
              baseActivity.toolbar.elevation = 4f
              baseActivity.toolbar.setWillNotDraw(false)
              baseActivity.dl_view.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
              return inflater.inflate(R.layout.fragment_about, container, false)
       }

       override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
              super.onViewCreated(view, savedInstanceState)
              button.setOnClickListener(this)
       }

       override fun onClick(p0: View?) {
              Toast.makeText(view?.context, listSmile[ThreadLocalRandom.current().nextInt(0, 5 + 1)], Toast.LENGTH_LONG).show()
       }
}
