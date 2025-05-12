package com.example.doon98.Models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.doon98.RoomDb.AppDatabase
import com.example.doon98.RoomDb.Category
import kotlinx.coroutines.launch

class CategoryViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: CategoryRepository

    init {
        val categoryDao = AppDatabase.getDatabase(application).categoryDao()
        repository = CategoryRepository(categoryDao)
    }

    fun insert(category: Category) = viewModelScope.launch {
        repository.insert(category)
    }

    fun getCategoriesByUser(userId: Int) = liveData {
        emit(repository.getCategoriesByUser(userId))
    }

    fun delete(category: Category) = viewModelScope.launch {
        repository.delete(category)
    }
}