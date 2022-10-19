package com.example.myapplication.Api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {

    val BASE_URL2 = "https://dummyjson.com/"

    private fun getLoggingInterceptor(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        var client = OkHttpClient.Builder().addInterceptor(interceptor).build()
        return client

    }
    fun getInstance1() : Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL2)
            .addConverterFactory(GsonConverterFactory.create())
            .client(getLoggingInterceptor())
            .build()

    }
}