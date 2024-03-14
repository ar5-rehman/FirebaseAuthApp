package com.authApp.presentation.sign_up

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import com.authApp.domain.model.Response.Loading
import com.authApp.domain.model.Response.Success
import com.authApp.domain.repository.AddUserResponse
import com.authApp.domain.repository.AuthRepository
import com.authApp.domain.repository.SendEmailVerificationResponse
import com.authApp.domain.repository.SignUpResponse
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val repo: AuthRepository
): ViewModel() {
    var signUpResponse by mutableStateOf<SignUpResponse>(Success(false))
    var sendEmailVerificationResponse by mutableStateOf<SendEmailVerificationResponse>(Success(false))
    var addUserResponse by mutableStateOf<AddUserResponse>(Success(false))

    fun signUpWithEmailAndPassword(email: String, password: String) = viewModelScope.launch {
        signUpResponse = Loading
        signUpResponse = repo.firebaseSignUpWithEmailAndPassword(email, password)
    }

    fun sendEmailVerification() = viewModelScope.launch {
        sendEmailVerificationResponse = Loading
        sendEmailVerificationResponse = repo.sendEmailVerification()
    }

    fun addUserName(userName: String) = viewModelScope.launch {
        addUserResponse = Loading
        addUserResponse = repo.addUserNameToFirestore(userName)
    }
}