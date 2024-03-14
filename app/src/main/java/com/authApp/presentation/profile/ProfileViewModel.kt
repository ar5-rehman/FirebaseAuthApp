package com.authApp.presentation.profile

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
import com.authApp.domain.repository.ReloadUserResponse
import com.authApp.domain.repository.RevokeAccessResponse
import com.authApp.domain.repository.UsersResponse
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repo: AuthRepository
): ViewModel() {
    var revokeAccessResponse by mutableStateOf<RevokeAccessResponse>(Success(false))
    var reloadUserResponse by mutableStateOf<ReloadUserResponse>(Success(false))
    var userNameResponse by mutableStateOf<UsersResponse>(Loading)

    init {
        getUserName()
    }

    fun reloadUser() = viewModelScope.launch {
        reloadUserResponse = Loading
        reloadUserResponse = repo.reloadFirebaseUser()
    }

    val isEmailVerified get() = repo.currentUser?.isEmailVerified ?: false

    fun signOut() = repo.signOut()

    fun revokeAccess() = viewModelScope.launch {
        revokeAccessResponse = Loading
        revokeAccessResponse = repo.revokeAccess()
    }

    private fun getUserName() = viewModelScope.launch {
        repo.getUsersFromFirestore(repo.currentUser?.uid.toString()).collect { response ->
            userNameResponse = response
        }
    }


}