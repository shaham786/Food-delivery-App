package com.example.myapplication.Repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.Api.ProductsApi
import com.example.myapplication.Models.ProductList

class ProductsRepository(private val productService : ProductsApi) {

    private val productsLiveData = MutableLiveData<ProductList>()

    val products : LiveData<ProductList>
    get() = productsLiveData

    suspend fun getProducts(page:Int):LiveData<ProductList>{
        val result = productService.getProducts(page)
        if (result?.body() != null){
            productsLiveData.postValue(result.body())
        }
        return
    }
}