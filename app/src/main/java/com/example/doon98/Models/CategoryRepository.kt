package com.example.doon98.Models

import com.example.doon98.RoomDb.Category
import com.example.doon98.RoomDb.CategoryDao

class CategoryRepository(private val categoryDao: CategoryDao) {
    suspend fun insert(category: Category) = categoryDao.insert(category)
    suspend fun getCategoriesByUser(userId: Int) = categoryDao.getCategoriesByUser(userId)
    suspend fun delete(category: Category) = categoryDao.delete(category)
}