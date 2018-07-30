package com.example.beardie.currencyholder.di.module

import android.app.Application
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.example.beardie.currencyholder.data.SharedPrefRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {
    @Provides
    @Singleton
    fun provideSharedPreference(app: Application): SharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(app)

    @Provides
    @Singleton
    fun provideSharedPrefRepository(spref: SharedPreferences): SharedPrefRepository =
            SharedPrefRepository(spref)
}