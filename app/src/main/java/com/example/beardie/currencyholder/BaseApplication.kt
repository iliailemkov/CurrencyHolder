package com.example.beardie.currencyholder

import com.crashlytics.android.Crashlytics
import com.example.beardie.currencyholder.di.DaggerAppComponent
import com.squareup.leakcanary.LeakCanary
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import io.fabric.sdk.android.Fabric


class BaseApplication : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent
                .builder()
                .create(this)
                .build()
    }

    override fun onCreate() {
        super.onCreate()
        Fabric.with(this, Crashlytics())
        LeakCanary.install(this)
        Thread.setDefaultUncaughtExceptionHandler { paramThread, paramThrowable ->
            Crashlytics.logException(paramThrowable)
        }

    }
}