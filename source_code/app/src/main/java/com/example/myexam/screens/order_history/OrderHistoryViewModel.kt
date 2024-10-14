package com.example.myexam.screens.order_history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myexam.data.Order
import com.example.myexam.data.ProductRepository
import com.example.myexam.data.ShoppingCartProduct
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class OrderHistoryViewModel : ViewModel() {

    private val _cartOrders = MutableStateFlow<List<ShoppingCartProduct>>(emptyList())
    val cartOrders = _cartOrders

    private val _loading = MutableStateFlow(false)
    val loading = _loading

    private val _orders = MutableStateFlow<List<Order>>(emptyList())
    val orders = _orders

    private val _quantity = MutableStateFlow(0)
    val quantity = _quantity

    private val _orderPrice = MutableStateFlow(0.0)
    val orderPrice = _orderPrice

    init {
        loadOrders()
    }

    fun loadOrders() {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.value = true
            try {
                val listOfOrders = ProductRepository.getOrderList()
                _orders.value = listOfOrders
                _cartOrders.value = ProductRepository.getCartsByIds(listOfOrders.map { it.orderId })
                updateQuantity(listOfOrders)
                _orderPrice.value = calculateOrderPrice(listOfOrders)
            } finally {
                _loading.value = false
            }
        }
    }

    private fun updateQuantity(listOfOrders: List<Order>) {
        _quantity.value = listOfOrders.sumOf { it.quantity }
    }

    private fun calculateOrderPrice(listOfOrders: List<Order>): Double {
        return listOfOrders.sumOf { it.orderPrice * it.quantity }
    }
}