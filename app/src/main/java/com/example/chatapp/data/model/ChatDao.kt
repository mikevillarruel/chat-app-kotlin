package com.example.chatapp.data.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ChatDao {
    @Query("SELECT * FROM chatentity")
    suspend fun getAllChats(): List<ChatEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveChat(movieEntity: ChatEntity)
}