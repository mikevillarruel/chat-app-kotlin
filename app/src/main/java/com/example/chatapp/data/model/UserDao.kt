package com.example.chatapp.data.model

import androidx.room.*

@Dao
interface UserDao {
    @Query("SELECT * FROM userentity")
    suspend fun getAllUsers(): List<UserEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUser(userEntity: UserEntity)

    @Query("DELETE FROM userentity")
    suspend fun deleteUsers()
}