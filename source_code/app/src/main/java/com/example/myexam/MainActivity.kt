package com.example.myexam

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myexam.data.ProductRepository
import com.example.myexam.screens.order_history.OrderHistoryScreen
import com.example.myexam.screens.order_history.OrderHistoryViewModel
import com.example.myexam.screens.product_details.ProductDetailsScreen
import com.example.myexam.screens.product_details.ProductDetailsViewModel
import com.example.myexam.screens.product_overview.ProductOverviewScreen
import com.example.myexam.screens.product_overview.ProductOverviewViewModel
import com.example.myexam.screens.shopping_cart.ShoppingCartScreen
import com.example.myexam.screens.shopping_cart.ShoppingCartViewModel
import com.example.myexam.ui.theme.MyExamTheme

class MainActivity : ComponentActivity() {
    // These under here are the viewModels that we will use in our screens.
    private val _productOverviewViewModel: ProductOverviewViewModel by viewModels()
    private val _productDetailsViewModel: ProductDetailsViewModel by viewModels()
    private val _shoppingCartViewModel: ShoppingCartViewModel by viewModels()
    private val _orderHistoryViewModel: OrderHistoryViewModel by viewModels()

    // This function is called when the activity is created.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // This function initializes the database.
        ProductRepository.initializeDatabase(applicationContext)

        // This function sets the content view of the activity.
        setContent {
            MyExamTheme {
                val navController = rememberNavController() // This is the navController that we will use to navigate between screens.

                NavHost(
                    navController = navController,
                    startDestination = "productOverviewScreen"
                ) {
                    composable(
                        route = "productOverviewScreen"
                    ) {
                        ProductOverviewScreen(
                            viewModel = _productOverviewViewModel,
                            onProductClick = { productId ->
                                navController.navigate("productDetailsScreen/$productId") // This navigates to the productDetailsScreen.
                            },
                            onCartClick = {
                                navController.navigate("shoppingCartScreen") // This navigates to the shoppingCartScreen.
                            }
                        ) {
                            navController.navigate("orderHistoryScreen") // This navigates to the orderHistoryScreen.
                        }
                    }

                    // This composable defines the productDetailsScreen. It takes the productId as an argument, and uses it to set the selected product in the viewModel.
                    composable(
                        route = "productDetailsScreen/{productId}",
                        arguments = listOf(
                            navArgument(name = "productId") {// This is the argument that we will pass to the productDetailsScreen.
                                type = NavType.IntType
                            }
                        )
                    ) { backStackEntry ->
                        val productId = backStackEntry.arguments?.getInt("productId") // This gets the productId from the arguments.

                        LaunchedEffect(productId) {
                            if (productId != null) {
                                _productDetailsViewModel.setSelectedProduct(productId)
                            }
                        }

                        ProductDetailsScreen(
                            viewModel = _productDetailsViewModel,
                            onBackButtonClick = {
                                navController.navigate("productOverviewScreen")
                            },
                            navController = navController,
                            onCartClick = {
                                navController.navigate("shoppingCartScreen")
                            },
                            onOrderHistoryClick = {
                                navController.navigate("orderHistoryScreen")
                            }
                        )

                    }

                    composable(route = "shoppingCartScreen") {
                        // LaunchedEffect will run it's code block first time we navigate to favoriteListScreen
                        LaunchedEffect(Unit) {
                            _shoppingCartViewModel.loadShoppingCart()
                        }

                        ShoppingCartScreen(
                            viewModel = _shoppingCartViewModel,
                            onBackButtonClick = { navController.navigate("productOverviewScreen") },
                            onOrderHistoryClick = { navController.navigate("orderHistoryScreen") },
                            /*onProductClick = { productId ->
                                navController.navigate("productDetailsScreen/$productId")
                            }*/
                        )
                    }

                    composable(route = "orderHistoryScreen") {
                        LaunchedEffect(Unit) {
                            _orderHistoryViewModel.loadOrders()
                        }

                        OrderHistoryScreen(
                            viewModel = _orderHistoryViewModel,
                            onBackButtonClick = { navController.navigate("productOverviewScreen") },
                            onCartClick = { navController.navigate("shoppingCartScreen") },
                        )
                    }

                }
            }
        }
    }
}
