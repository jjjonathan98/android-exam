package com.example.myexam.screens.product_details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage

@Composable
fun ProductDetailsScreen(
    viewModel: ProductDetailsViewModel,
    onBackButtonClick: () -> Unit = {},
    onCartClick: () -> Unit = {},
    navController: NavController,
    onOrderHistoryClick: () -> Unit = {}
) {
    // This loads the product
    val loading = viewModel.loading.collectAsState()
    // This gets the selected product from the state
    val productState = viewModel.selectedProduct.collectAsState()

    // If the product is still loading, then a progress indicator is shown
    if(loading.value) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }

    // If we are here, then the product has been loaded. So, we can safely get the product object from the state
    val product = productState.value
    if(product == null) {
        Text(text = "Failed to get product details. Selected product object is NULL!")
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { onBackButtonClick() },
                modifier = Modifier.size(36.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back to Product Overview"
                )
            }
            Text(
                modifier = Modifier
                    .padding(8.dp)
                    .weight(1f),
                text = "Product Details",
                style = MaterialTheme.typography.titleLarge
            )

            IconButton(
                onClick = {onCartClick()
                          navController.navigate("shoppingCartScreen")
                          },
                modifier = Modifier.size(36.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = "Shopping Cart",
                )
            }

            IconButton(
                onClick = {onOrderHistoryClick()},
                modifier = Modifier.size(36.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.List,
                    contentDescription = "Product List",
                )
            }
        }

        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            model = product.image,
            contentScale = ContentScale.Crop,
            alignment = Alignment.TopCenter,
            contentDescription = "Image of ${product.title}"
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 16.dp)
                .verticalScroll(rememberScrollState()),
        ) {
            Text(
                text = product.title,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = product.description,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Price: $${product.price}",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Add-to-Cart button
            Button(
                onClick = {
                    viewModel.addToCart(product)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .align(Alignment.CenterHorizontally),
            ) {
                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = "Add to Cart",
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Add to Cart",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White
                )
            }
        }
    }
}
