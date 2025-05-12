package com.example.doon98.Models

import com.example.doon98.RoomDb.Expense
import com.example.doon98.RoomDb.ExpenseDao


class ExpenseRepository(private val expenseDao: ExpenseDao) {
    suspend fun insert(expense: Expense) = expenseDao.insert(expense)
    suspend fun getExpensesByUser(userId: Int) = expenseDao.getExpensesByUser(userId)
    suspend fun getExpensesByDateRange(userId: Int, startDate: Long, endDate: Long) =
        expenseDao.getExpensesByDateRange(userId, startDate, endDate)
    suspend fun getCategoryTotal(userId: Int, categoryId: Int, startDate: Long, endDate: Long) =
        expenseDao.getCategoryTotal(userId, categoryId, startDate, endDate)
    suspend fun delete(expense: Expense) = expenseDao.delete(expense)
}