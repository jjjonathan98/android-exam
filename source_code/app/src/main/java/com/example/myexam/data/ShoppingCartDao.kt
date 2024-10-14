package com.example.myexam.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ShoppingCartDao {
    @Query("SELECT * FROM ShoppingCartProduct")
    suspend fun getShoppingCartProducts(): List<ShoppingCartProduct>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShoppingCartProduct(shoppingCartProduct: ShoppingCartProduct)

    @Delete
    suspend fun removeShoppingCartProduct(shoppingCartProduct: ShoppingCartProduct)

    @Query("SELECT * FROM orders")
    suspend fun getOrderList(): List<Order>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrder(order: Order)

    @Query("SELECT * FROM ShoppingCartProduct WHERE productId IN (:productIds)")
    suspend fun getCartsByIds(productIds: List<Int>): List<ShoppingCartProduct>
}