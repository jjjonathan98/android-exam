package com.example.myexam.data

import androidx.room.Entity
import androidx.room.PrimaryKey

// This annotation tells Room to create a table with the name "orders".
@Entity (tableName = "orders")
data class Order(
    @PrimaryKey
    val orderId: Int, // This is the primary key for the table.
    val quantity: Int,
    val orderPrice: Double
)