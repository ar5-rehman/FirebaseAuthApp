package com.authApp.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import com.authApp.core.Constants.EMAIL_LABEL

@Composable
fun EmailField(
    email: TextFieldValue,
    isError: Boolean = false,
    errorText: String = "",
    onEmailValueChange: (newValue: TextFieldValue) -> Unit
) {

    OutlinedTextField(
        value = email,
        onValueChange = { newValue ->
            onEmailValueChange(newValue)
        },
        label = {
            Text(
                text = EMAIL_LABEL
            )
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email
        ),
        isError = isError,
        supportingText = {
            if (isError) {
                ErrorTextInputField(text = errorText)
            }
        }
    )
}