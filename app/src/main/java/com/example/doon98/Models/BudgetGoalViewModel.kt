package com.example.doon98.Models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.doon98.RoomDb.AppDatabase
import com.example.doon98.RoomDb.BudgetGoal
import kotlinx.coroutines.launch

class BudgetGoalViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: BudgetGoalRepository

    init {
        val budgetGoalDao = AppDatabase.getDatabase(application).budgetGoalDao()
        repository = BudgetGoalRepository(budgetGoalDao)
    }

    fun insert(goal: BudgetGoal) = viewModelScope.launch {
        repository.insert(goal)
    }

    fun getBudgetGoal(userId: Int, month: Int, year: Int) = liveData {
        emit(repository.getBudgetGoal(userId, month, year))
    }

    fun update(goal: BudgetGoal) = viewModelScope.launch {
        repository.update(goal)
    }
}