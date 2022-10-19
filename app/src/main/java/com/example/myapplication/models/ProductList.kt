package com.example.myapplication.models

data class ProductList(
    val limit: Int,
    val products: ArrayList<Product>,
    val skip: Int,
    val total: Int
)