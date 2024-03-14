package com.authApp.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.authApp.domain.model.Response
import com.authApp.presentation.sign_in.SignInViewModel
import com.google.android.gms.auth.api.identity.BeginSignInResult

@Composable
fun OneTapSignIn(
    viewModel: SignInViewModel = hiltViewModel(),
    launch: (result: BeginSignInResult) -> Unit
) {
    when(val oneTapSignInResponse = viewModel.oneTapSignInResponse) {
        is Response.Loading -> ProgressBar()
        is Response.Success -> oneTapSignInResponse.data?.let {
            LaunchedEffect(it) {
                launch(it)
            }
        }
        is Response.Failure -> LaunchedEffect(Unit) {
            print(oneTapSignInResponse.e)
        }
    }
}