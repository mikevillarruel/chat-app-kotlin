package com.example.chatapp.domain.home

import com.example.chatapp.core.InternetCheck
import com.example.chatapp.data.local.LocalHomeDataSource
import com.example.chatapp.data.model.Message
import com.example.chatapp.data.model.User
import com.example.chatapp.data.model.toUserEntity
import com.example.chatapp.data.remote.home.HomeDataSource
import com.google.firebase.firestore.DocumentReference
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

class HomeRepoImpl(
    private val homeDataSource: HomeDataSource,
    private val localHomeDataSource: LocalHomeDataSource
) : HomeRepo {

    override suspend fun getUsers(): List<User> {
        if (InternetCheck.isNetworkAvailable()) {
            localHomeDataSource.deleteUsers()
            homeDataSource.getUsers().forEach { user ->
                localHomeDataSource.saveUser(user.toUserEntity())
            }
        }
        return localHomeDataSource.getUsers()
    }

    override suspend fun sendMessage(message: Message, receiver: String): DocumentReference? {
        return homeDataSource.sendMessage(message, receiver)
    }

    @ExperimentalCoroutinesApi
    override suspend fun getChat(receiver: String): Flow<List<Message>> =
        homeDataSource.getChat(receiver)
}