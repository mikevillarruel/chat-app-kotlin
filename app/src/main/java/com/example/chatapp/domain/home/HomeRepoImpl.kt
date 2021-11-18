package com.example.chatapp.domain.home

import com.example.chatapp.data.model.User
import com.example.chatapp.data.remote.home.HomeDataSource

class HomeRepoImpl(private val homeDataSource: HomeDataSource) : HomeRepo {
    override suspend fun getUsers(): List<User> = homeDataSource.getUsers()
}