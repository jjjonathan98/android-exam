package com.example.myexam.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Product( // This class represents the Product table.
    @PrimaryKey
    val id: Int, // This is the primary key for the table.
    val title: String,
    val price: Double,
    val image: String,
    val description: String,
    val category: String,
)
