package com.example.beardie.currencyholder

import com.example.beardie.currencyholder.di.DaggerAppComponent
import com.squareup.leakcanary.LeakCanary
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication


class BaseApplication : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent
                .builder()
                .create(this)
                .build()
    }

    override fun onCreate() {
        super.onCreate()
        LeakCanary.install(this)
    }
}