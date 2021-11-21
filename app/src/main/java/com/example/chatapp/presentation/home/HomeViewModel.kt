package com.example.chatapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.example.chatapp.core.Result
import com.example.chatapp.data.model.Message
import com.example.chatapp.domain.home.HomeRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect

class HomeViewModel(private val homeRepo: HomeRepo) : ViewModel() {

    fun getUsers() = liveData(Dispatchers.IO) {
        emit(Result.Loading())
        try {
            emit(Result.Success(homeRepo.getUsers()))
        } catch (e: Exception) {
            emit(Result.Failure(e))
        }
    }

    fun sendMessage(message: Message, receiver: String) = liveData(Dispatchers.IO) {
        emit(Result.Loading())
        try {
            emit(Result.Success(homeRepo.sendMessage(message, receiver)))
        } catch (e: Exception) {
            emit(Result.Failure(e))
        }
    }

    fun getChat(receiver: String) = liveData(Dispatchers.IO) {
        emit(Result.Loading())
        try {
            homeRepo.getChat(receiver).collect {
                emit(Result.Success(it))
            }
        } catch (e: Exception) {
            emit(Result.Failure(e))
        }
    }

}

class HomeViewModelFactory(private val homeRepo: HomeRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomeViewModel(homeRepo) as T
    }
}