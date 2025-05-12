package com.example.doon98.Models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.doon98.RoomDb.AppDatabase
import com.example.doon98.RoomDb.User
import kotlinx.coroutines.launch


class UserViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: UserRepository

    init {
        val userDao = AppDatabase.getDatabase(application).userDao()
        repository = UserRepository(userDao)
    }

    fun insert(user: User) = viewModelScope.launch {
        repository.insert(user)
    }

    fun getUser(username: String, password: String) = liveData {
        emit(repository.getUser(username, password))
    }

    fun checkUsernameExists(username: String) = liveData {
        emit(repository.checkUsernameExists(username))
    }
}