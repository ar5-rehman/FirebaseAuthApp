package com.authApp.presentation.verify_email.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.authApp.components.ProgressBar
import com.authApp.core.Utils.Companion.print
import com.authApp.domain.model.Response.*
import com.authApp.presentation.profile.ProfileViewModel

@Composable
fun ReloadUser(
    viewModel: ProfileViewModel = hiltViewModel(),
    navigateToProfileScreen: () -> Unit
) {
    when(val reloadUserResponse = viewModel.reloadUserResponse) {
        is Loading -> ProgressBar()
        is Success -> {
            val isUserReloaded = reloadUserResponse.data
            LaunchedEffect(isUserReloaded) {
                if (isUserReloaded == true) {
                    navigateToProfileScreen()
                }
            }
        }
        is Failure -> reloadUserResponse.apply {
            LaunchedEffect(e) {
                print(e)
            }
        }
    }
}