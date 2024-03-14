package com.authApp.presentation.sign_in

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.authApp.domain.model.Response
import com.authApp.domain.repository.AuthRepository
import com.authApp.domain.repository.OneTapSignInResponse
import com.authApp.domain.repository.SignInResponse
import com.authApp.domain.repository.SignInWithGoogleResponse
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.AuthCredential
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val repo: AuthRepository,
    val oneTapClient: SignInClient
): ViewModel() {

    var signInResponse by mutableStateOf<SignInResponse>(Response.Success(false))
    var oneTapSignInResponse by mutableStateOf<OneTapSignInResponse>(Response.Success(null))
    var signInWithGoogleResponse by mutableStateOf<SignInWithGoogleResponse>(Response.Success(false))

    fun signInWithEmailAndPassword(email: String, password: String) = viewModelScope.launch {
        signInResponse = Response.Loading
        signInResponse = repo.firebaseSignInWithEmailAndPassword(email, password)
    }

    fun oneTapSignIn() = viewModelScope.launch {
        oneTapSignInResponse = Response.Loading
        oneTapSignInResponse = repo.oneTapSignInWithGoogle()
    }

    fun signInWithGoogle(googleCredential: AuthCredential) = viewModelScope.launch {
        signInWithGoogleResponse = Response.Loading
        signInWithGoogleResponse = repo.firebaseSignInWithGoogle(googleCredential)
    }
}