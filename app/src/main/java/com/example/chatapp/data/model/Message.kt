package com.example.chatapp.data.model

data class Message(
    val content: String = "",
    val type: MessageType = MessageType.TEXT
)