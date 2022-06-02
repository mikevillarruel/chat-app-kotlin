package com.example.chatapp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.ServerTimestamp
import java.util.*

data class Message(
    val uid: String = "",
    val content: String = "",
    val latLng: LocationLatLng? = null,
    val imageUrl: String = "",
    val type: String = MessageType.TEXT.value,
    @ServerTimestamp
    val timestamp: Date? = null
)

data class LocationLatLng(
    val latitude: Double = 0.0,
    val longitude: Double = 0.0
)

@Entity
data class MessageEntity(
    @PrimaryKey val uid: String = "",
    @ColumnInfo(name = "content") val content: String = "",
    @ColumnInfo(name = "latLng") val latLng: LocationLatLng? = null,
    @ColumnInfo(name = "imageUrl") val imageUrl: String = "",
    @ColumnInfo(name = "type") val type: String = MessageType.TEXT.value,
    @ColumnInfo(name = "timestamp") val timestamp: Date? = null
)

@Entity
data class LocationLatLngEntity(
    @PrimaryKey val uid: String = "",
    @ColumnInfo(name = "latitude") val latitude: Double = 0.0,
    @ColumnInfo(name = "longitude") val longitude: Double = 0.0
)