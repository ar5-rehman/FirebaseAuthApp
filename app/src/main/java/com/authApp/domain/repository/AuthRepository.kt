package com.authApp.domain.repository

import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow
import com.authApp.domain.model.Response
import com.authApp.domain.model.Users
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.firebase.auth.AuthCredential
import kotlinx.coroutines.flow.Flow

typealias SignUpResponse = Response<Boolean>
typealias SendEmailVerificationResponse = Response<Boolean>
typealias SignInResponse = Response<Boolean>
typealias ReloadUserResponse = Response<Boolean>
typealias SendPasswordResetEmailResponse = Response<Boolean>
typealias RevokeAccessResponse = Response<Boolean>
typealias AuthStateResponse = StateFlow<Boolean>
typealias OneTapSignInResponse = Response<BeginSignInResult>
typealias SignInWithGoogleResponse = Response<Boolean>

typealias UsersResponse = Response<String>
typealias AddUserResponse = Response<Boolean>

interface AuthRepository {
    val currentUser: FirebaseUser?

    val isUserAuthenticatedInFirebase: Boolean

    suspend fun firebaseSignUpWithEmailAndPassword(email: String, password: String): SignUpResponse

    suspend fun sendEmailVerification(): SendEmailVerificationResponse

    suspend fun firebaseSignInWithEmailAndPassword(email: String, password: String): SignInResponse

    suspend fun reloadFirebaseUser(): ReloadUserResponse

    suspend fun sendPasswordResetEmail(email: String): SendPasswordResetEmailResponse

    fun signOut()

    suspend fun revokeAccess(): RevokeAccessResponse

    fun getAuthState(viewModelScope: CoroutineScope): AuthStateResponse

    suspend fun oneTapSignInWithGoogle(): OneTapSignInResponse

    suspend fun firebaseSignInWithGoogle(googleCredential: AuthCredential): SignInWithGoogleResponse

    suspend fun addUserNameToFirestore(UserName: String): AddUserResponse

    fun getUsersFromFirestore(id: String): Flow<UsersResponse>

}
