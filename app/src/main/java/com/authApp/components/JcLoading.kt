package com.authApp.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun JcLoading(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize()) {
        CircularProgressIndicator(
            modifier = Modifier
                .align(Alignment.Center)
                .size(100.dp)
                .padding(PaddingValues(24.dp)),
            color = MaterialTheme.colors.primary,
            strokeWidth = 4.dp
        )
    }
}

@Composable
fun JcLinearLoading() {
    Box {
        LinearProgressIndicator(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
            color = MaterialTheme.colors.primary
        )
    }
}