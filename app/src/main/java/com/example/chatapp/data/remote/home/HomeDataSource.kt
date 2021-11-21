package com.example.chatapp.data.remote.home

import com.example.chatapp.data.model.Chat
import com.example.chatapp.data.model.Message
import com.example.chatapp.data.model.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class HomeDataSource {

    private val db by lazy { Firebase.firestore }
    private val auth by lazy { Firebase.auth }

    suspend fun getUsers(): List<User> {
        val querySnapshot = db.collection("user").get().await()
        val usersList = mutableListOf<User>()
        querySnapshot.forEach { user ->
            if (user.id != auth.currentUser?.uid) {
                user.toObject(User::class.java).let { newUser ->
                    usersList.add(newUser)
                }
            }
        }

        return usersList
    }

    suspend fun sendMessage(message: Message, receiver: String): DocumentReference? {

        val sender: String = auth.currentUser?.uid.toString()
        /* Sender */
        val documentReference = send(message, sender, receiver)
        /* Receiver */
        send(message, receiver, sender)
        return documentReference
    }

    private suspend fun send(
        message: Message,
        sender: String,
        receiver: String
    ): DocumentReference? {
        val documentSnapshot =
            db.collection("chat").document("${sender}-${receiver}").get().await()

        if (!documentSnapshot.exists()) {
            db
                .collection("chat")
                .document("${sender}-${receiver}")
                .set(
                    Chat(
                        sender,
                        receiver
                    )
                ).await()
        }

        val documentReference = db
            .collection("chat")
            .document("${sender}-${receiver}")
            .collection("messages")
            .add(
                message
            ).await()

        return documentReference
    }

    @ExperimentalCoroutinesApi
    suspend fun getChat(receiver: String): Flow<List<Message>> = callbackFlow {
        val messageList = mutableListOf<Message>()
        val sender: String = auth.currentUser?.uid.toString()


        var query: Query? = null
        try {
            query =
                db
                    .collection("chat")
                    .document("${sender}-${receiver}")
                    .collection("messages")
                    .orderBy("timestamp", Query.Direction.DESCENDING)
        } catch (e: Throwable) {
            close(e)
        }

        val subscription = query?.addSnapshotListener { value, error ->
            if (value == null) return@addSnapshotListener
            try {
                messageList.clear()
                value.documents.forEach { message ->
                    message.toObject(Message::class.java)?.let { newMessage ->
                        messageList.add(newMessage)
                    }
                }
            } catch (e: Throwable) {
                close(e)
            }

            offer(messageList)
        }

        awaitClose { subscription?.remove() }
    }
}