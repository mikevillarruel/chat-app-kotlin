package com.example.chatapp.data.local

import com.example.chatapp.data.model.User
import com.example.chatapp.data.model.UserDao
import com.example.chatapp.data.model.UserEntity
import com.example.chatapp.data.model.toUserList

class LocalHomeDataSource(private val userDao: UserDao) {

    suspend fun getUsers(): List<User> {
        return userDao.getAllUsers().toUserList()
    }

    suspend fun saveUser(user: UserEntity) {
        userDao.saveUser(user)
    }

    suspend fun deleteUsers() {
        userDao.deleteUsers()
    }
}