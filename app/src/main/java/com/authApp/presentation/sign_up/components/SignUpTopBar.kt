package com.authApp.presentation.sign_up.components

import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import com.authApp.components.BackIcon
import com.authApp.core.Constants.SIGN_UP_SCREEN

@Composable
fun SignUpTopBar(
    navigateBack: () -> Unit
) {
    TopAppBar (
        title = {
            Text(
                text = SIGN_UP_SCREEN
            )
        },
        navigationIcon = {
            BackIcon(
                navigateBack = navigateBack
            )
        }
    )
}