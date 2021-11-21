package com.example.chatapp.domain.home

import com.example.chatapp.data.model.Message
import com.example.chatapp.data.model.User
import com.example.chatapp.data.remote.home.HomeDataSource
import com.google.firebase.firestore.DocumentReference
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

class HomeRepoImpl(private val homeDataSource: HomeDataSource) : HomeRepo {

    override suspend fun getUsers(): List<User> = homeDataSource.getUsers()

    override suspend fun sendMessage(message: Message, receiver: String): DocumentReference? {
        return homeDataSource.sendMessage(message, receiver)
    }

    @ExperimentalCoroutinesApi
    override suspend fun getChat(receiver: String): Flow<List<Message>> =
        homeDataSource.getChat(receiver)
}