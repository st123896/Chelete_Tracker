package com.example.doon98.Models

import com.example.doon98.RoomDb.User
import com.example.doon98.RoomDb.UserDao

/**
 * Repository for User operations
 * Abstracts data sources from ViewModel
 */

class UserRepository(private val userDao: UserDao) {
    // Insert a new user
    suspend fun insert(user: User) = userDao.insert(user)
    // Get user by credentials
    suspend fun getUser(username: String, password: String) = userDao.getUser(username, password)
    // Get user by credentials
    suspend fun checkUsernameExists(username: String) = userDao.checkUsernameExists(username)
}