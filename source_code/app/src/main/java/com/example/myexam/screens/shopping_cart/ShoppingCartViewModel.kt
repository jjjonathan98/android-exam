package com.example.myexam.screens.shopping_cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myexam.data.Order
import com.example.myexam.data.Product
import com.example.myexam.data.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ShoppingCartViewModel : ViewModel() {
    val orderPlaced = MutableStateFlow(false)
    private val _shoppingCartProducts = MutableStateFlow<List<Product>>(emptyList())
    val shoppingCartProducts = _shoppingCartProducts.asStateFlow()

    // This function loads the shopping cart
    fun loadShoppingCart() {
        viewModelScope.launch(Dispatchers.IO) {
            val listOfShoppingCartProductIds = ProductRepository.getShoppingCartProducts().map { it.productId} // This gets the list of product ids in the shopping cart
            _shoppingCartProducts.value = ProductRepository.getProductsByIds(listOfShoppingCartProductIds)
        }
    }

    // This function removes a product from the shopping cart
    fun removeProductFromCart(productId: Int) {
        viewModelScope.launch {
            val cartItem = ProductRepository.getShoppingCartProducts().find { it.productId == productId }
            if (cartItem != null) {
                ProductRepository.removeShoppingCartProduct(cartItem)
                loadShoppingCart()
            }
        }
    }

    // This function places an order
    fun placeOrder() {
        viewModelScope.launch(Dispatchers.IO) {
            val listOfShoppingCartProducts = ProductRepository.getShoppingCartProducts()
            if (listOfShoppingCartProducts.isNotEmpty()) {
                val order = Order( // This creates an order object
                    orderId = System.currentTimeMillis().toInt(), // This is a unique id for the order
                    quantity = listOfShoppingCartProducts.sumOf { it.quantity }, // This is the total quantity of products in the order
                    orderPrice = listOfShoppingCartProducts.sumOf { it.price } // This is the total price of the order
                )
                ProductRepository.addOrder(order) // This adds the order to the database

                // This removes all the products from the shopping cart
                listOfShoppingCartProducts.forEach { shoppingCart ->
                    ProductRepository.removeShoppingCartProduct(shoppingCart)
                }

                // This refreshes the shopping cart
                loadShoppingCart()
            }
        }
    }
}