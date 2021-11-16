package com.example.chatapp.data.remote.auth

import com.example.chatapp.data.model.User
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class AuthDataSource {

    private val auth by lazy { Firebase.auth }

    suspend fun signUp(email: String, password: String, displayName: String): FirebaseUser? {
        val authResult = auth.createUserWithEmailAndPassword(email, password).await()
        authResult.user?.uid?.let { uid ->
            val firestore = Firebase.firestore
            firestore
                .collection("user")
                .document(uid)
                .set(
                    User(email, displayName, "")
                )
                .await()
        }
        return authResult.user
    }

    suspend fun signIn(email: String, password: String): FirebaseUser? {
        val authResult = auth.signInWithEmailAndPassword(email, password).await()
        return authResult.user
    }

}