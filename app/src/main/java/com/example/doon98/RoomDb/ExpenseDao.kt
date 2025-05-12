package com.example.doon98.RoomDb

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
// Data Access Object (DAO) for Expense entity operations
// Defines methods to interact with the expenses table in the database
@Dao// Marks this interface as a Room DAO
interface ExpenseDao {
    @Insert
    suspend fun insert(expense: Expense)

    @Query("SELECT * FROM expenses WHERE user_id = :userId")
    suspend fun getExpensesByUser(userId: Int): List<Expense>

    @Query("SELECT * FROM expenses WHERE user_id = :userId AND date BETWEEN :startDate AND :endDate")
    suspend fun getExpensesByDateRange(userId: Int, startDate: Long, endDate: Long): List<Expense>

    @Query("SELECT SUM(amount) FROM expenses WHERE user_id = :userId AND category_id = :categoryId AND date BETWEEN :startDate AND :endDate")
    suspend fun getCategoryTotal(userId: Int, categoryId: Int, startDate: Long, endDate: Long): Double

    @Delete
    suspend fun delete(expense: Expense)
}