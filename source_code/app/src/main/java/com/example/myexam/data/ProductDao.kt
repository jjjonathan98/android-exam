package com.example.myexam.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface ProductDao{
    @Query("SELECT * FROM Product")
    suspend fun getProducts(): List<Product> // This function returns all the products in the table.

    @Query("SELECT * FROM Product WHERE :productId = id")
    suspend fun getProductById(productId: Int): Product? // This function returns the product with the given id.

    @Query("SELECT * FROM Product WHERE id IN (:idList)")
    suspend fun getProductsByIds(idList: List<Int>): List<Product> // This function returns the products with the given ids.

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducts(products: List<Product>) // This function inserts the given products into the table.
}