package com.news.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.news.R

@Composable
fun ErrorSnackBar(
    message: String
) {
    Snackbar(containerColor = Color.Red, modifier = Modifier.clip(RoundedCornerShape(25.dp))) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ) {
            Icon(
                painterResource(id = R.drawable.ic_error),
                contentDescription = null
            )
            Text(modifier = Modifier.padding(start = 10.dp), text = message)
        }
    }
}