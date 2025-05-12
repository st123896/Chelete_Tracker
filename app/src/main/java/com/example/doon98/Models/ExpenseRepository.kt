package com.example.doon98.Models

import com.example.doon98.RoomDb.Expense
import com.example.doon98.RoomDb.ExpenseDao

/**
 * Repository for Expense operations
 * Abstracts data source operations from ViewModel
 */
class ExpenseRepository(private val expenseDao: ExpenseDao) {
    // Insert new expense
    suspend fun insert(expense: Expense) = expenseDao.insert(expense)
    // Get all expenses for a user
    suspend fun getExpensesByUser(userId: Int) = expenseDao.getExpensesByUser(userId)
    // Get expenses within date range
    suspend fun getExpensesByDateRange(userId: Int, startDate: Long, endDate: Long) =
        expenseDao.getExpensesByDateRange(userId, startDate, endDate)
    // Get total amount for a category within date range
    suspend fun getCategoryTotal(userId: Int, categoryId: Int, startDate: Long, endDate: Long) =
        expenseDao.getCategoryTotal(userId, categoryId, startDate, endDate)
    // Delete an expense
    suspend fun delete(expense: Expense) = expenseDao.delete(expense)
}