package com.authApp.presentation.forgot_password

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import com.authApp.domain.model.Response.Loading
import com.authApp.domain.model.Response.Success
import com.authApp.domain.repository.AuthRepository
import com.authApp.domain.repository.SendPasswordResetEmailResponse
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val repo: AuthRepository
): ViewModel() {
    var sendPasswordResetEmailResponse by mutableStateOf<SendPasswordResetEmailResponse>(Success(false))

    fun sendPasswordResetEmail(email: String) = viewModelScope.launch {
        sendPasswordResetEmailResponse = Loading
        sendPasswordResetEmailResponse = repo.sendPasswordResetEmail(email)
    }
}