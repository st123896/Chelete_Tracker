package com.example.doon98.Models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.doon98.RoomDb.AppDatabase
import com.example.doon98.RoomDb.BudgetGoal
import kotlinx.coroutines.launch
/**
 * ViewModel for BudgetGoal operations
 * Provides budget goal data to UI
 */
class BudgetGoalViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: BudgetGoalRepository

    // Initialize repository with BudgetGoalDao from database
    init {
        val budgetGoalDao = AppDatabase.getDatabase(application).budgetGoalDao()
        repository = BudgetGoalRepository(budgetGoalDao)
    }
    // Insert new budget goal in background thread
    fun insert(goal: BudgetGoal) = viewModelScope.launch {
        repository.insert(goal)
    }
    // Get budget goal as LiveData
    fun getBudgetGoal(userId: Int, month: Int, year: Int) = liveData {
        emit(repository.getBudgetGoal(userId, month, year))
    }
    // Update budget goal in background thread
    fun update(goal: BudgetGoal) = viewModelScope.launch {
        repository.update(goal)
    }
}