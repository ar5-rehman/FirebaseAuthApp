package com.authApp.presentation.sign_up.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.authApp.components.ProgressBar
import com.authApp.core.Utils.Companion.print
import com.authApp.domain.model.Response.*
import com.authApp.presentation.sign_up.SignUpViewModel

@Composable
fun SendEmailVerification(
    viewModel: SignUpViewModel = hiltViewModel()
) {
    when(val sendEmailVerificationResponse = viewModel.sendEmailVerificationResponse) {
        is Loading -> ProgressBar()
        is Success -> Unit
        is Failure -> sendEmailVerificationResponse.apply {
            LaunchedEffect(e) {
                print(e)
            }
        }
    }
}