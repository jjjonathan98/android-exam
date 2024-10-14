package com.example.myexam.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ShoppingCartProduct")
data class ShoppingCartProduct(
    @PrimaryKey
    val productId: Int,
    val quantity: Int,
    val price: Double
)
