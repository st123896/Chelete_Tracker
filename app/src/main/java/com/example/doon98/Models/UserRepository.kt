package com.example.doon98.Models

import com.example.doon98.RoomDb.User
import com.example.doon98.RoomDb.UserDao


class UserRepository(private val userDao: UserDao) {
    suspend fun insert(user: User) = userDao.insert(user)
    suspend fun getUser(username: String, password: String) = userDao.getUser(username, password)
    suspend fun checkUsernameExists(username: String) = userDao.checkUsernameExists(username)
}