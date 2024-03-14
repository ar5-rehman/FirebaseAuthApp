package com.authApp.presentation.sign_up

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.authApp.components.ProgressBar
import com.authApp.core.Constants
import com.authApp.core.Constants.VERIFY_EMAIL_MESSAGE
import com.authApp.core.Utils
import com.authApp.core.Utils.Companion.showMessage
import com.authApp.domain.model.Response
import com.authApp.presentation.sign_up.components.SendEmailVerification
import com.authApp.presentation.sign_up.components.SignUp
import com.authApp.presentation.sign_up.components.SignUpContent
import com.authApp.presentation.sign_up.components.SignUpTopBar

@Composable
@ExperimentalComposeUiApi
fun SignUpScreen(
    viewModel: SignUpViewModel = hiltViewModel(),
    navigateBack: () -> Unit
) {
    val context = LocalContext.current
    var userName by rememberSaveable {
        mutableStateOf("")
    }

    Scaffold(
        topBar = {
            SignUpTopBar(
                navigateBack = navigateBack
            )
        },
        content = { padding ->
            SignUpContent(
                padding = padding,
                signUp = { name, email, password ->
                    userName = name
                    viewModel.signUpWithEmailAndPassword(email, password)
                },
                navigateBack = navigateBack
            )
        }
    )

    SignUp(
        sendEmailVerification = {
            viewModel.sendEmailVerification()
        },
        showVerifyEmailMessage = {
            showMessage(context, VERIFY_EMAIL_MESSAGE)
        },
        saveUserName = {
            viewModel.addUserName(userName)
        },
        showErrorMessage = { errorMessage ->
            showMessage(context, errorMessage)
        }
    )

    SendEmailVerification()
}