package com.example.myapplication.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.myapplication.models.Product

@Dao
interface ProductDao {

    @Insert
    suspend fun addProduct(products : List<Product>)


    @Query("SELECT * FROM product")
    suspend fun getProduct1a() : List<Product>
}