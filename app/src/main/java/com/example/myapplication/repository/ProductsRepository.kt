package com.example.myapplication.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.api.ProductsApi
import com.example.myapplication.db.ProductDatabase
import com.example.myapplication.models.Product
import com.example.myapplication.models.ProductList
import com.example.myapplication.utils.NetworkUtils

class ProductsRepository(
    private val productService: ProductsApi,
    private val productDatabase: ProductDatabase,
    private val applicationContext: Context
) {

    private val productsLiveData = MutableLiveData<ProductList>()

    val products : LiveData<ProductList>
        get() = productsLiveData

    suspend fun getProducts(page:Int){

        if(NetworkUtils.isInternetAvailable(applicationContext)){

            val result = productService.getProducts(page)
            if (result.body() != null){
                productDatabase.productDao().addProduct(result.body()!!.products)
                productsLiveData.postValue(result.body())
            }

        }
        else{
            val  products = productDatabase.productDao().getProduct1()
            val productList = ProductList(1, products as ArrayList<Product>,1,1)
            productsLiveData.postValue(productList)
        }



    }
}