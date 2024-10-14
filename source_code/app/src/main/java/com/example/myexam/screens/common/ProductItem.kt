package com.example.myexam.screens.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.myexam.data.Product

@Composable
fun ProductItem( // This function defines the ProductItem composable.
    product: Product,
    onClick: () -> Unit = {},
    onRemoveClick: () -> Unit = {},
    removeButton: Boolean = false
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 12.dp,
                vertical = 8.dp
            )
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(10)
            )
            .background(color = Color.White)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {

        AsyncImage(
            modifier = Modifier
                .size(108.dp, 108.dp)
                .background(color = Color.Gray),
            model = product.image,
            alignment = Alignment.Center,
            contentScale = ContentScale.Crop,
            contentDescription = "Image of ${product.title}"
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = product.title,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = "Category: ${product.category}",
                fontWeight = FontWeight.SemiBold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = "$${product.price}",
                fontWeight = FontWeight.SemiBold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }


        if (removeButton) { // This if statement checks if the removeButton parameter is true.
            IconButton(
                onClick = { onRemoveClick() },
                modifier = Modifier
                    .size(36.dp)
                    .padding(8.dp)
            ) {
                Icon( // This is the remove button.
                    imageVector = Icons.Default.Clear,
                    contentDescription = "Remove from Cart"
                )
            }

        }
    }
}