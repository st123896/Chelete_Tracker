package com.example.doon98.Models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.doon98.RoomDb.AppDatabase
import com.example.doon98.RoomDb.Expense
import kotlinx.coroutines.launch

class ExpenseViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: ExpenseRepository

    init {
        val expenseDao = AppDatabase.getDatabase(application).expenseDao()
        repository = ExpenseRepository(expenseDao)
    }

    fun insert(expense: Expense) = viewModelScope.launch {
        repository.insert(expense)
    }

    fun getExpensesByUser(userId: Int) = liveData {
        emit(repository.getExpensesByUser(userId))
    }

    fun getExpensesByDateRange(userId: Int, startDate: Long, endDate: Long) = liveData {
        emit(repository.getExpensesByDateRange(userId, startDate, endDate))
    }

    fun getCategoryTotal(userId: Int, categoryId: Int, startDate: Long, endDate: Long) = liveData {
        emit(repository.getCategoryTotal(userId, categoryId, startDate, endDate))
    }

    fun delete(expense: Expense) = viewModelScope.launch {
        repository.delete(expense)
    }
}