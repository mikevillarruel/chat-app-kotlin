package com.example.chatapp.domain.auth

import com.example.chatapp.data.remote.auth.AuthDataSource
import com.google.firebase.auth.FirebaseUser

class AuthRepoImpl(private val authDataSource: AuthDataSource) : AuthRepo {

    override suspend fun signUp(
        email: String,
        password: String,
        displayName: String
    ): FirebaseUser? {
        return authDataSource.signUp(email, password, displayName)
    }

    override suspend fun signIn(email: String, password: String): FirebaseUser? {
        return authDataSource.signIn(email, password)
    }

}