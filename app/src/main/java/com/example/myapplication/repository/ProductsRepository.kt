package com.example.myapplication.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.api.ProductsApi
import com.example.myapplication.db.ProductDatabase
import com.example.myapplication.models.ProductList

class ProductsRepository(private val productService : ProductsApi, private val productDatabase: ProductDatabase) {

    private val productsLiveData = MutableLiveData<ProductList>()

    val products : LiveData<ProductList>
        get() = productsLiveData

    suspend fun getProducts(page:Int){
        val result = productService.getProducts(page)
        if (result.body() != null){
            productDatabase.productDao().addProduct(result.body()!!.products)
            productsLiveData.postValue(result.body())
        }
        return
    }
}