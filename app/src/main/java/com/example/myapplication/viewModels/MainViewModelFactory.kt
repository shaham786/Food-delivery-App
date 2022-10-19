package com.example.myapplication.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.repository.ProductsRepository

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory(private val repository: ProductsRepository) : ViewModelProvider.Factory  {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(repository) as T
    }
}