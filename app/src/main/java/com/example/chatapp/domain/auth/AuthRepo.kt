package com.example.chatapp.domain.auth

import com.google.firebase.auth.FirebaseUser

interface AuthRepo {
    suspend fun signUp(email: String, password: String, displayName: String): FirebaseUser?
    suspend fun signIn(email: String, password: String): FirebaseUser?
}