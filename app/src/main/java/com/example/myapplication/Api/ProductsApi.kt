package com.example.myapplication.Api

import com.example.myapplication.Models.ProductList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductsApi {

    @GET("/products")
    suspend fun getProducts(@Query("page")page : Int ): Response<ProductList>
}