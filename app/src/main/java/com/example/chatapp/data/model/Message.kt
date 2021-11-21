package com.example.chatapp.data.model

import com.google.firebase.firestore.ServerTimestamp
import java.util.*

data class Message(
    val uid: String = "",
    val content: String = "",
    val type: String = MessageType.TEXT.value,
    @ServerTimestamp
    val timestamp: Date? = null
)