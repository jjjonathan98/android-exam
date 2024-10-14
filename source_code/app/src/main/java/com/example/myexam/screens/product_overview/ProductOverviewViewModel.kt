package com.example.myexam.screens.product_overview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myexam.data.Product
import com.example.myexam.data.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductOverviewViewModel : ViewModel() {

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products = _products.asStateFlow()

    init {
        loadProducts()
    }

    // This function loads the products, and sets the loading state to true while the products are being loaded. Once the products are loaded, the loading state is set to false.
    fun loadProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.value = true
            _products.value = ProductRepository.getProducts()
            _loading.value = false
        }
    }
}