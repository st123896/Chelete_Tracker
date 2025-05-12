package com.example.doon98.RoomDb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


// Room Database class that serves as the main access point to the persisted data
// Defines the database configuration and provides DAOs (Data Access Objects)
@Database(
    // List of all entity classes that are part of this database
    entities = [User::class, Category::class, Expense::class, BudgetGoal::class],
    // Database version (increment when schema changes)
    version = 1,
    // Set to false if you don't want to keep schema version history
    exportSchema = false
)
// Abstract methods that provide access to each DAO
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao// DAO for User entity operations
    abstract fun categoryDao(): CategoryDao// DAO for Category entity operations
    abstract fun expenseDao(): ExpenseDao  // DAO for Expense entity operations
    abstract fun budgetGoalDao(): BudgetGoalDao // DAO for BudgetGoal entity operations

    // Companion object to implement the Singleton pattern for the database instance
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            // Synchronized block to prevent concurrent database creation
            return INSTANCE ?: synchronized(this) {

                val instance = Room.databaseBuilder(
                    context.applicationContext,// Application context to avoid memory leaks
                    AppDatabase::class.java,// Database class reference
                    "doon98_db"// Database file name
                ).build()  // Build the database
                INSTANCE = instance
                instance
            }
        }
    }
}