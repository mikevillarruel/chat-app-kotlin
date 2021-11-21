package com.example.chatapp.domain.home

import com.example.chatapp.data.model.Message
import com.example.chatapp.data.model.User
import com.google.firebase.firestore.DocumentReference
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

interface HomeRepo {
    suspend fun getUsers(): List<User>
    suspend fun sendMessage(message: Message, receiver: String): DocumentReference?
    @ExperimentalCoroutinesApi
    suspend fun getChat(receiver: String): Flow<List<Message>>
}