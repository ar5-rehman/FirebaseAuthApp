package com.authApp.presentation.sign_up.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.sp
import com.authApp.components.EmailField
import com.authApp.components.PasswordField
import com.authApp.components.SmallSpacer
import com.authApp.components.UserNameField
import com.authApp.core.Constants.ALREADY_USER
import com.authApp.core.Constants.EMPTY_STRING
import com.authApp.core.Constants.SIGN_UP_BUTTON

@Composable
@ExperimentalComposeUiApi
fun SignUpContent(
    padding: PaddingValues,
    signUp: (userName: String, email: String, password: String) -> Unit,
    navigateBack: () -> Unit
) {
    var userName by rememberSaveable(
        stateSaver = TextFieldValue.Saver,
        init = {
            mutableStateOf(
                value = TextFieldValue(
                    text = EMPTY_STRING
                )
            )
        }
    )
    var email by rememberSaveable(
        stateSaver = TextFieldValue.Saver,
        init = {
            mutableStateOf(
                value = TextFieldValue(
                    text = EMPTY_STRING
                )
            )
        }
    )
    var password by rememberSaveable(
        stateSaver = TextFieldValue.Saver,
        init = {
            mutableStateOf(
                value = TextFieldValue(
                    text = EMPTY_STRING
                )
            )
        }
    )
    val keyboard = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        UserNameField(
            userName = userName,
            onEmailValueChange = { newValue ->
                userName = newValue
            }
        )
        SmallSpacer()
        EmailField(
            email = email,
            onEmailValueChange = { newValue ->
                email = newValue
            }
        )
        SmallSpacer()
        PasswordField(
            password = password,
            onPasswordValueChange = { newValue ->
                password = newValue
            }
        )
        SmallSpacer()
        Button(
            onClick = {
                keyboard?.hide()
                signUp(userName.text, email.text, password.text)
            }
        ) {
            Text(
                text = SIGN_UP_BUTTON,
                fontSize = 15.sp
            )
        }
        Text(
            modifier = Modifier.clickable {
                navigateBack()
            },
            text = ALREADY_USER,
            fontSize = 15.sp
        )
    }
}