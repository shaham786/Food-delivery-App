package com.example.myapplication.api

import com.example.myapplication.models.ProductList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductsApi {

    @GET("/products")
    suspend fun getProducts(@Query("page")page : Int ): Response<ProductList>
}