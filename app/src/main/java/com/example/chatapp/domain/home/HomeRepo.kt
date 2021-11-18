package com.example.chatapp.domain.home

import com.example.chatapp.data.model.User

interface HomeRepo {
    suspend fun getUsers(): List<User>
}