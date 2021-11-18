package com.example.chatapp.data.model

data class Chat(
    val sender: String = "",
    val receiver: String = "",
    val messages: List<Message> = listOf()
)
