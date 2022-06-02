package com.example.chatapp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

data class Chat(
    val sender: String = "",
    val receiver: String = ""
)

@Entity
data class ChatEntity(
    @PrimaryKey val id: String = "",
    @ColumnInfo(name = "sender") val sender: String = "",
    @ColumnInfo(name = "receiver") val receiver: String = "",
    @ColumnInfo(name = "messages") val messages: List<Message> = mutableListOf()
)

