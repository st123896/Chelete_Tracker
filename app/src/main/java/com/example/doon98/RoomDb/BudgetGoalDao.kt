package com.example.doon98.RoomDb

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface BudgetGoalDao {
    @Insert
    suspend fun insert(goal: BudgetGoal)

    @Query("SELECT * FROM budget_goals WHERE user_id = :userId AND month = :month AND year = :year")
    suspend fun getBudgetGoal(userId: Int, month: Int, year: Int): BudgetGoal?

    @Update
    suspend fun update(goal: BudgetGoal)
}