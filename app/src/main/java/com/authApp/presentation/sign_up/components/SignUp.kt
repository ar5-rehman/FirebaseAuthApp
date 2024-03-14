package com.authApp.presentation.sign_up.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.authApp.components.ProgressBar
import com.authApp.core.Utils.Companion.print
import com.authApp.domain.model.Response.*
import com.authApp.presentation.sign_up.SignUpViewModel

@Composable
fun SignUp(
    viewModel: SignUpViewModel = hiltViewModel(),
    sendEmailVerification: () -> Unit,
    saveUserName: () -> Unit,
    showVerifyEmailMessage: () -> Unit,
    showErrorMessage: (errorMessage: String?) -> Unit
) {
    when(val signUpResponse = viewModel.signUpResponse) {
        is Loading -> ProgressBar()
        is Success -> {
            val isUserSignedUp = signUpResponse.data
            LaunchedEffect(isUserSignedUp) {
                if (isUserSignedUp == true) {
                    sendEmailVerification()
                    showVerifyEmailMessage()
                    saveUserName()
                }
            }
        }
        is Failure -> signUpResponse.apply {
            LaunchedEffect(e) {
                showErrorMessage(e.message)
                print(e)
            }
        }
    }
}