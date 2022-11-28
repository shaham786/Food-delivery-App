package com.example.myapplication.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "product")
data class Product(
    @ColumnInfo(name="brand")
    @SerializedName("brand")
    val brand: String?,
    val category: String,
    val description: String,
    val discountPercentage: Double,
    @PrimaryKey
    val id: Int,
    val images: List<String>,
    val price: Int,
    val rating: Double,
    val stock: Int,
    val thumbnail: String,
    val title: String
) : Serializable