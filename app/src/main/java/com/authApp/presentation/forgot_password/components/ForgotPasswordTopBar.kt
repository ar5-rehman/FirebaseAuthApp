package com.authApp.presentation.forgot_password.components

import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import com.authApp.components.BackIcon
import com.authApp.core.Constants.FORGOT_PASSWORD_SCREEN

@Composable
fun ForgotPasswordTopBar(
    navigateBack: () -> Unit
) {
    TopAppBar (
        title = {
            Text(
                text = FORGOT_PASSWORD_SCREEN
            )
        },
        navigationIcon = {
            BackIcon(
                navigateBack = navigateBack
            )
        }
    )
}