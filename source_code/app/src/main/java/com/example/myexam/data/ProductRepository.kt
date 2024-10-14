package com.example.myexam.data

import android.content.Context
import android.util.Log
import androidx.room.Room
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ProductRepository {

    private val _httpClient = // This is the HTTP client that Retrofit will use to make network requests.
        OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY)
            )
            .build() // This builds the HTTP client.

    private val _retrofit = // This is the Retrofit object that will be used to make network requests.
        Retrofit.Builder()
            .client(_httpClient)
            .baseUrl("https://fakestoreapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    private val _productService = _retrofit.create(ProductService::class.java)

    private lateinit var _appDatabase: AppDatabase
    private val _productDao by lazy { _appDatabase.productDao() }
    private val _shoppingCartDao by lazy { _appDatabase.shoppingCartDao() }

    fun initializeDatabase(context: Context) { // This function initializes the database.
        _appDatabase = Room.databaseBuilder(
            context = context,
            klass = AppDatabase::class.java,
            name = "product-database"
        ).fallbackToDestructiveMigration().build()
    }

    suspend fun getProducts(): List<Product> { // This function returns all the products.
        try {
            val response = _productService.getAllProducts()

            if (response.isSuccessful) {
                val products = response.body() ?: emptyList()

                _productDao.insertProducts(products)

                return _productDao.getProducts()

            } else {
                val errorMessage = response.errorBody()?.string() ?: "Unknown error"
                Log.e("getProducts", "Error: ${response.code()}, $errorMessage")
                throw Exception("Error: getProducts is not successful")
            }
        } catch (e: Exception) {
            Log.e("ProductRepository", "Repository didn't work", e)
            return _productDao.getProducts()
        }
    }

    suspend fun getProductById(productId: Int): Product? { // This function returns the product with the given id.
        return _productDao.getProductById(productId)
    }

    suspend fun getProductsByIds(idList: List<Int>): List<Product> { // This function returns the products with the given ids.
        return _productDao.getProductsByIds(idList)
    }

    // --- Shopping Cart Functions ---

    suspend fun getShoppingCartProducts(): List<ShoppingCartProduct> { // This function returns all the products in the shopping cart.
        return _shoppingCartDao.getShoppingCartProducts()
    }

    suspend fun addShoppingCartProduct(shoppingCartProduct: ShoppingCartProduct) { // This function adds the given product to the shopping cart.
        return _shoppingCartDao.insertShoppingCartProduct(shoppingCartProduct)
    }

    suspend fun removeShoppingCartProduct(shoppingCartProduct: ShoppingCartProduct) { // This function removes the given product from the shopping cart.
        return _shoppingCartDao.removeShoppingCartProduct(shoppingCartProduct)
    }

    // --- Order Functions ---
    suspend fun getOrderList(): List<Order> { // This function returns all the orders.
        return _shoppingCartDao.getOrderList()
    }

    suspend fun addOrder(order: Order) { // This function adds the given order to the database.
        return _shoppingCartDao.insertOrder(order)
    }

    suspend fun getCartsByIds(idList: List<Int>): List<ShoppingCartProduct> { // This function returns the shopping cart products with the given ids.
        return _shoppingCartDao.getCartsByIds(idList)
    }
}