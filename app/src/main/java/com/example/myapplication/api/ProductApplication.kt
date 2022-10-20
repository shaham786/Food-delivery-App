package com.example.myapplication.api

import android.app.Application
import com.example.myapplication.db.ProductDatabase
import com.example.myapplication.repository.ProductsRepository

class ProductApplication : Application() {

    lateinit var productsRepository: ProductsRepository

    override fun onCreate() {
        super.onCreate()
        initialize()
    }

    private fun initialize(){
        val productsAPI = RetrofitHelper.getInstance1().create(ProductsApi::class.java)
        val database = ProductDatabase.getDatabase(applicationContext)
        productsRepository = ProductsRepository(productsAPI, database)

    }
}