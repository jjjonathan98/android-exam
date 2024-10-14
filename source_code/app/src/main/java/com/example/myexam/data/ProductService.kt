package com.example.myexam.data

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductService { // This interface defines the endpoints for the API.
    @GET("products")
    suspend fun getAllProducts(): Response<List<Product>>

    @GET("products/{id}")
    suspend fun getProductById(
        @Path("id") id: Int
    ): Response<Product>

}