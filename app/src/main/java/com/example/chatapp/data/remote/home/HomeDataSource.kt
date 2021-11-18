package com.example.chatapp.data.remote.home

import com.example.chatapp.data.model.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class HomeDataSource {

    private val db by lazy { Firebase.firestore }

    suspend fun getUsers(): List<User> {
        val querySnapshot = db.collection("user").get().await()
        val usersList = mutableListOf<User>()
        querySnapshot.forEach { user ->
            user.toObject(User::class.java)?.let { newUser ->
                usersList.add(newUser)
            }
        }

        return usersList
    }
}