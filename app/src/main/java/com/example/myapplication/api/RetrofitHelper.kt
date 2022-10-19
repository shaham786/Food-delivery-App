package com.example.myapplication.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {

    private const val BASE_URL2 = "https://dummyjson.com/"

    private fun getLoggingInterceptor(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.apply { interceptor.level = HttpLoggingInterceptor.Level.BODY }
        return OkHttpClient.Builder().addInterceptor(interceptor).build()

    }
    fun getInstance1() : Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL2)
            .addConverterFactory(GsonConverterFactory.create())
            .client(getLoggingInterceptor())
            .build()

    }
}