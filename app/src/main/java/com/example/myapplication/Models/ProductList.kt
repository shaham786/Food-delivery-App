package com.example.myapplication.Models

import com.example.myapplication.Models.Product

data class ProductList(
    val limit: Int,
    val products: ArrayList<Product>,
    val skip: Int,
    val total: Int
)