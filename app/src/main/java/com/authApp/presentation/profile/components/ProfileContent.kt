package com.authApp.presentation.profile.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.authApp.core.Constants.WELCOME_MESSAGE
import com.authApp.domain.model.Response
import com.authApp.presentation.profile.ProfileViewModel

@Composable
fun ProfileContent(
    padding: PaddingValues,
    viewModel: ProfileViewModel = hiltViewModel(),
) {

    when(val userNameResponse = viewModel.userNameResponse) {
        is Response.Loading -> {}
        is Response.Success -> {
            Box(
                modifier = Modifier.fillMaxSize().padding(padding).padding(top = 48.dp),
                contentAlignment = Alignment.TopCenter
            ) {
                Text(
                    text = WELCOME_MESSAGE +" "+ userNameResponse.data,
                    fontSize = 24.sp
                )
            }
        }
        is Response.Failure -> print(userNameResponse.e)
    }

}