package com.example.chatapp.domain.auth

import com.example.chatapp.data.remote.auth.AuthDataSource
import com.google.firebase.auth.FirebaseUser

class AuthRepoImpl(private val authDataSource: AuthDataSource) : AuthRepo {

    override suspend fun signUp(
        email: String,
        password: String,
        displayName: String
    ): FirebaseUser? = authDataSource.signUp(email, password, displayName)

}