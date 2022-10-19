package com.example.myapplication.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.Models.ProductList
import com.example.myapplication.Repository.ProductsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val repository: ProductsRepository): ViewModel() {

    init {
        viewModelScope.launch(Dispatchers.IO) {
        repository.getProducts(1)
        }
    }
    val product : LiveData<ProductList>
    get() = repository.products

 }