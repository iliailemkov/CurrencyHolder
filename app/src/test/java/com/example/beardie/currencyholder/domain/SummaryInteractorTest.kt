package com.example.beardie.currencyholder.domain

import android.content.Context
import android.content.SharedPreferences
import com.example.beardie.currencyholder.data.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito
import org.junit.Before
import org.mockito.ArgumentMatchers.*


@RunWith(JUnit4::class)
class SummaryInteractorTest {

    val sharedPrefs = Mockito.mock(SharedPreferences::class.java)
    val context = Mockito.mock(Context::class.java)

    val sharedPrefRepository = SharedPrefRepository(sharedPrefs)

    val summaryInteractor : SummaryInteractor = SummaryInteractor(
            sharedPrefRepository,
            TransactionRepository(),
            CategoryRepository())

    @Before
    @Throws(Exception::class)
    fun before() {
        Mockito.`when`(context.getSharedPreferences(anyString(), anyInt())).thenReturn(sharedPrefs)
    }

    @Test
    fun shouldEqualPreference() {
        Mockito.`when`(sharedPrefs.getBoolean(anyString(), anyBoolean())).thenReturn(true)
        assert(sharedPrefRepository.getShowlegend())
        assert(sharedPrefRepository.getOnlyOutcomes())
    }
}