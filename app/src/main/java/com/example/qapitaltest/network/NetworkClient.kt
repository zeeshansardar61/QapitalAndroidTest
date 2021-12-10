package com.example.qapitaltest.network

import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit
import okhttp3.OkHttpClient
import okhttp3.OkHttpClient.Builder

class NetworkClient {

    var retrofit: Retrofit? = null

    fun getRetrofitMethod(): Retrofit {
        if (retrofit == null) {
            val builder = Builder()
            val okHttpClient: OkHttpClient = builder.build()
            retrofit = Retrofit.Builder()
                .baseUrl("http://qapital-ios-testtask.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build()
        }
        return retrofit!!
    }
}