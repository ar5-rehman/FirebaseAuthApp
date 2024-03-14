package com.authApp.data.repository

import com.authApp.core.Constants.DISPLAY_NAME
import com.authApp.core.Constants.SIGN_IN_REQUEST
import com.authApp.core.Constants.SIGN_UP_REQUEST
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.tasks.await
import com.authApp.domain.model.Response.Failure
import com.authApp.domain.model.Response.Success
import com.authApp.domain.model.Users
import com.authApp.domain.repository.AuthRepository
import com.authApp.domain.repository.OneTapSignInResponse
import com.authApp.domain.repository.SignInWithGoogleResponse
import com.authApp.domain.repository.UsersResponse
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private var oneTapClient: SignInClient,
    @Named(SIGN_IN_REQUEST)
    private var signInRequest: BeginSignInRequest,
    @Named(SIGN_UP_REQUEST)
    private var signUpRequest: BeginSignInRequest,
    private val db: FirebaseFirestore
) : AuthRepository {
    override val currentUser get() = auth.currentUser

    override suspend fun firebaseSignUpWithEmailAndPassword(
        email: String, password: String
    ) = try {
        auth.createUserWithEmailAndPassword(email, password).await()
        Success(true)
    } catch (e: Exception) {
        Failure(e)
    }

    override suspend fun sendEmailVerification() = try {
        auth.currentUser?.sendEmailVerification()?.await()
        Success(true)
    } catch (e: Exception) {
        Failure(e)
    }

    override suspend fun firebaseSignInWithEmailAndPassword(
        email: String, password: String
    ) = try {
        auth.signInWithEmailAndPassword(email, password).await()
        Success(true)
    } catch (e: Exception) {
        Failure(e)
    }

    override suspend fun reloadFirebaseUser() = try {
        auth.currentUser?.reload()?.await()
        Success(true)
    } catch (e: Exception) {
        Failure(e)
    }

    override suspend fun sendPasswordResetEmail(email: String) = try {
        auth.sendPasswordResetEmail(email).await()
        Success(true)
    } catch (e: Exception) {
        Failure(e)
    }

    override fun signOut() {
        oneTapClient.signOut()
        auth.signOut()
    }

    override suspend fun revokeAccess() = try {
        auth.currentUser?.delete()?.await()
        Success(true)
    } catch (e: Exception) {
        Failure(e)
    }

    override fun getAuthState(viewModelScope: CoroutineScope) = callbackFlow {
        val authStateListener = AuthStateListener { auth ->
            trySend(auth.currentUser == null)
        }
        auth.addAuthStateListener(authStateListener)
        awaitClose {
            auth.removeAuthStateListener(authStateListener)
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), auth.currentUser == null)

    override val isUserAuthenticatedInFirebase = auth.currentUser != null

    override suspend fun oneTapSignInWithGoogle(): OneTapSignInResponse {
        return try {
            val signInResult = oneTapClient.beginSignIn(signInRequest).await()
            Success(signInResult)
        } catch (e: Exception) {
            try {
                val signUpResult = oneTapClient.beginSignIn(signUpRequest).await()
                Success(signUpResult)
            } catch (e: Exception) {
                Failure(e)
            }
        }
    }

    override suspend fun firebaseSignInWithGoogle(
        googleCredential: AuthCredential
    ): SignInWithGoogleResponse {
        return try {
            val authResult = auth.signInWithCredential(googleCredential).await()
            val isNewUser = authResult.additionalUserInfo?.isNewUser ?: false
            if (isNewUser) {
                addGoogleUserToFirestore()
            }
            Success(true)
        } catch (e: Exception) {
            Failure(e)
        }
    }

    override fun getUsersFromFirestore(id: String): Flow<UsersResponse> = callbackFlow {
        val snapshotListener = db.collection("UserName").document(id).addSnapshotListener { snapshot, e ->
            val usersResponse = if (snapshot != null) {
                val users = snapshot.getString(DISPLAY_NAME)
                Success(users)
            } else {
                Failure(e!!)
            }
            trySend(usersResponse)
        }
        awaitClose {
            snapshotListener.remove()
        }
    }

    override suspend fun addUserNameToFirestore(userName: String) = try {
        val user = Users(
            displayName = userName
        )
        addUserToFirestore(user)
        Success(true)
    } catch (e: Exception) {
        Failure(e)
    }

    private suspend fun addUserToFirestore(user: Users) {
        auth.currentUser?.apply {
            db.collection("UserName").document(uid).set(user).await()
        }
    }

    private suspend fun addGoogleUserToFirestore() {
        auth.currentUser?.apply {
            val user = toGoogleUser()
            db.collection("UserName").document(uid).set(user).await()
        }
    }
}

fun FirebaseUser.toGoogleUser() = mapOf(
    DISPLAY_NAME to displayName
)