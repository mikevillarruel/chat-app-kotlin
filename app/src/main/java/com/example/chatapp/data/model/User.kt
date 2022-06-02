package com.example.chatapp.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val uid: String = "",
    val email: String = "",
    val displayName: String = "",
    val photoUrl: String = ""
) : Parcelable

@Entity
data class UserEntity(
    @PrimaryKey val uid: String = "",
    @ColumnInfo(name = "email") val email: String = "",
    @ColumnInfo(name = "displayName") val displayName: String = "",
    @ColumnInfo(name = "photoUrl") val photoUrl: String = ""
)

fun UserEntity.toUser(): User = User(
    this.uid,
    this.email,
    this.displayName,
    this.photoUrl
)

fun User.toUserEntity(): UserEntity = UserEntity(
    this.uid,
    this.email,
    this.displayName,
    this.photoUrl
)

fun List<UserEntity>.toUserList(): List<User> {
    val resultList= mutableListOf<User>()
    this.forEach { userEntity ->
        resultList.add(userEntity.toUser())
    }
    return resultList
}