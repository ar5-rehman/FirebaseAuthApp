package com.authApp.presentation.sign_in.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.authApp.components.ProgressBar
import com.authApp.core.Utils.Companion.print
import com.authApp.domain.model.Response.*
import com.authApp.presentation.sign_in.SignInViewModel

@Composable
fun SignIn(
    viewModel: SignInViewModel = hiltViewModel(),
    showErrorMessage: (errorMessage: String?) -> Unit
) {
    when(val signInResponse = viewModel.signInResponse) {
        is Loading -> ProgressBar()
        is Success -> Unit
        is Failure -> signInResponse.apply {
            LaunchedEffect(e) {
                print(e)
                showErrorMessage(e.message)
            }
        }
    }
}