package com.authApp.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import com.authApp.core.Constants.USER_NAME

@Composable
fun UserNameField(
    userName: TextFieldValue,
    isError: Boolean = false,
    errorText: String = "",
    onEmailValueChange: (newValue: TextFieldValue) -> Unit
) {

    OutlinedTextField(
        value = userName,
        onValueChange = { newValue ->
            onEmailValueChange(newValue)
        },
        label = {
            Text(
                text = USER_NAME
            )
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text
        ),
        isError = isError,
        supportingText = {
            if (isError) {
                ErrorTextInputField(text = errorText)
            }
        }
    )
}