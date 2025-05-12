package com.example.doon98

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import com.example.doon98.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

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