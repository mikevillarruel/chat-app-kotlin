package com.example.chatapp.data.model

enum class MessageType(val value: String) {
    IMAGE("img"),
    TEXT("txt")
}

fun String.getMessageType(): MessageType {
    return when (this) {
        MessageType.IMAGE.value -> MessageType.IMAGE
        else -> MessageType.TEXT
    }
}