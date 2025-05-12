package com.example.doon98.Models

import com.example.doon98.RoomDb.BudgetGoal
import com.example.doon98.RoomDb.BudgetGoalDao

class BudgetGoalRepository(private val budgetGoalDao: BudgetGoalDao) {
    suspend fun insert(goal: BudgetGoal) = budgetGoalDao.insert(goal)
    suspend fun getBudgetGoal(userId: Int, month: Int, year: Int) =
        budgetGoalDao.getBudgetGoal(userId, month, year)
    suspend fun update(goal: BudgetGoal) = budgetGoalDao.update(goal)
}