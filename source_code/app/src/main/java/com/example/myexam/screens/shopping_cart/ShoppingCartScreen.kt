package com.example.myexam.screens.shopping_cart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.myexam.screens.common.ProductItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ShoppingCartScreen(
    viewModel: ShoppingCartViewModel,
    onBackButtonClick: () -> Unit = {},
    onOrderHistoryClick: () -> Unit = {}
) {
    // This is the state that we will use to show the snackbar
    var snackbarVisible by remember { mutableStateOf(false) }

    // This is the coroutine scope that we will use to show the snackbar
    val coroutineScope = rememberCoroutineScope()

    // This loads the shopping cart
    val products = viewModel.shoppingCartProducts.collectAsState()

    // This calculates the total price of the products in the shopping cart
    val totalPrice = products.value.sumOf { it.price }

    // This collects the orderPlaced state from the viewModel
    val orderPlaced = viewModel.orderPlaced.collectAsState()

    if (orderPlaced.value) {
        LaunchedEffect(Unit) {
            // Show snackbar for a short duration
            snackbarVisible = true
            delay(3000) // Adjust the duration as needed
            snackbarVisible = false
        }
    }

    // This is the snackbar host state that we will use to show the snackbar, when the order is placed
    val snackbarHostState = remember { SnackbarHostState() }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Header Row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { onBackButtonClick() }
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Navigate Back"
                )
            }

            Text(
                modifier = Modifier.padding(8.dp),
                text = "Shopping Cart",
                style = MaterialTheme.typography.titleLarge
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(end = 16.dp)
            ) {


                // Icons in the top right
                IconButton(
                    onClick = {
                        // Handle shopping cart icon click
                    },
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ShoppingCart,
                        contentDescription = "Shopping Cart"
                    )
                }

                IconButton(
                    onClick = {
                        // Handle order history icon click
                        onOrderHistoryClick()
                    },
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.List,
                        contentDescription = "Order History"
                    )
                }
            }
        }

        Divider()

        // This is the LazyColumn that displays the products in the shopping cart
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 48.dp, bottom = 68.dp)
        ) {
            items(products.value) { product ->
                ProductItem(
                    product = product,
                    onRemoveClick = {
                        viewModel.removeProductFromCart(product.id)
                    },
                    removeButton = true
                )
            }
        }

        // This is the checkout button
        Button(
            onClick = {
                // Places the order
                viewModel.placeOrder()

                // This shows the snackbar as soon as the order is placed
                coroutineScope.launch {
                    snackbarHostState.showSnackbar("Order Placed Successfully!")
                }
            },
            modifier = Modifier
                .fillMaxWidth(0.75f)
                .padding(16.dp)
                .align(Alignment.BottomCenter)
        ) {
            Text("Checkout - Total: $$totalPrice")
        }

        // Displays the snackbar using SnackbarHost
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            Snackbar(
                modifier = Modifier.padding(16.dp),
                content = {
                    Text("Order Placed Successfully!")
                }
            )
        }
    }
}