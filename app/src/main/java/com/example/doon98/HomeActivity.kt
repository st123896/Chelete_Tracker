package com.example.doon98

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import com.example.doon98.databinding.ActivityHomeBinding

/**
 * Main dashboard activity with navigation buttons
 * Features:
 * - Launch point for key features
 * - Buttons for adding expenses, managing categories, and setting budgets
 * - Intent-based navigation to other activities
 */
class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    // Setup click listeners for all main actions
    // Launch appropriate activities based on user selection
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // New button to view expenses
        binding.btnViewExpenses.setOnClickListener {
            startActivity(Intent(this, ExpenseListActivity::class.java))
        }

        // Set up button click listeners
        binding.btnAddExpense.setOnClickListener {
            startActivity(Intent(this, AddExpenseActivity::class.java))
        }

        binding.btnManageCategories.setOnClickListener {
            // Start CategoriesActivity or show CategoriesFragment
            val intent = Intent(this, MainActivity::class.java).apply {
                putExtra("open_fragment", "categories")
            }
            startActivity(intent)
        }

        binding.btnSetBudget.setOnClickListener {
            // Start BudgetActivity or show BudgetFragment
            val intent = Intent(this, MainActivity::class.java).apply {
                putExtra("open_fragment", "budget")
            }
            startActivity(intent)
        }
    }
}