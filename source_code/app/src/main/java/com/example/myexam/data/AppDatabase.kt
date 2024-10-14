package com.example.myexam.data

import androidx.room.Database
import androidx.room.RoomDatabase

// This annotation tells Room to create a database with the name "AppDatabase" and the version number.
@Database(
    entities = [Product::class, ShoppingCartProduct::class, Order::class],
    version = 2,
    exportSchema = false
)

// This class is the main access point for the database.
abstract class AppDatabase: RoomDatabase() {
    abstract fun productDao(): ProductDao // This function returns the DAO for the Product table.
    abstract fun shoppingCartDao(): ShoppingCartDao // This function returns the DAO for the ShoppingCartProduct table.
}

