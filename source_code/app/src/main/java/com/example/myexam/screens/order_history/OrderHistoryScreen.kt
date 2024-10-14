package com.example.myexam.screens.order_history

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.myexam.data.Order

@Composable
fun OrderHistoryScreen( // This function defines the OrderHistoryScreen composable.
    viewModel: OrderHistoryViewModel,
    onBackButtonClick: () -> Unit = {},
    onCartClick: () -> Unit = {},
    onOrderHistoryClick: () -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White),
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
            )
            {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back Button"
                )
            }

            Text(
                modifier = Modifier
                    .padding(8.dp)
                    .weight(1f),
                text = "Order History",
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(end = 16.dp)
            ) {
                IconButton(
                    onClick = { onCartClick() },
                    modifier = Modifier.size(36.dp)
                )
                {
                    Icon(
                        imageVector = Icons.Default.ShoppingCart,
                        contentDescription = "Shopping Cart"
                    )
                }

                IconButton(
                    onClick = { onOrderHistoryClick() },
                    modifier = Modifier.size(36.dp)
                )
                {
                    Icon(
                        imageVector = Icons.Default.List,
                        contentDescription = "Order History"
                    )
                }
            }
        }
    }

    // Displays the list of orders
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 32.dp, start = 16.dp, end = 16.dp, top = 64.dp),
        reverseLayout = true
    ) {
        items(viewModel.orders.value) { order ->
            OrderItem(order = order)
            Divider(color = Color.Gray, thickness = 1.dp)
        }
    }
}

@Composable
fun OrderItem(order: Order) { // This function decides how to display the order. How it will look like.
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // Display Order details (e.g., order number, date, total amount)
        Text(
            text = "Order number #${order.orderId}",
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = "Amount: ${order.quantity}",
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = "Total price: $${order.orderPrice}",
            style = MaterialTheme.typography.bodyMedium
        )
        // Add more order details as needed

        // Spacer for separation
        Spacer(modifier = Modifier.height(8.dp))
    }
}