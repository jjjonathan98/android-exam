package com.example.myexam.screens.product_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myexam.data.Product
import com.example.myexam.data.ProductRepository
import com.example.myexam.data.ShoppingCartProduct
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductDetailsViewModel : ViewModel() {
    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _selectedProduct = MutableStateFlow<Product?>(null)
    val selectedProduct = _selectedProduct.asStateFlow()

    private val _isInCart = MutableStateFlow(false)
    val isInCart = _isInCart.asStateFlow()

    fun setSelectedProduct(productId: Int) {
        viewModelScope.launch {
            _loading.value = true
            _selectedProduct.value = ProductRepository.getProductById(productId)
            _isInCart.value = isProductInShoppingCart()
            _loading.value = false
        }
    }

    private suspend fun isProductInShoppingCart(): Boolean {
        return ProductRepository.getShoppingCartProducts().any { it.productId == selectedProduct.value?.id }
    }

    fun addToCart(product: Product) {
        viewModelScope.launch(Dispatchers.IO) {
            val shoppingCartProduct = ShoppingCartProduct(
                productId = product.id,
                quantity = 1,
                price = product.price
            )
            ProductRepository.addShoppingCartProduct(shoppingCartProduct)
        }
    }
}