package com.example.beardie.currencyholder.data.api

import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface ExchangeApiService {

    @GET("/api/v6/convert")
    fun getExchangeRate(@Query("q") fromTo: String,
                        @Query("compact") compact: String): Call<ResponseBody>


    companion object {
        fun create(): ExchangeApiService {

            val retrofit = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS).build())
                    .baseUrl("http://free.currencyconverterapi.com/")
                    .build()

            return retrofit.create(ExchangeApiService::class.java)
        }
    }
}