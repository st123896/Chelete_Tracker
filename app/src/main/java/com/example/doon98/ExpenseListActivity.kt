package com.example.doon98

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.doon98.Models.ExpenseViewModel
import com.example.doon98.databinding.ActivityExpenseListBinding

class ExpenseListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityExpenseListBinding
    private lateinit var expenseViewModel: ExpenseViewModel
    private lateinit var adapter: ExpenseAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExpenseListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        expenseViewModel = ViewModelProvider(this)[ExpenseViewModel::class.java]

        setupRecyclerView()
        loadExpenses()
    }

    private fun setupRecyclerView() {
        adapter = ExpenseAdapter { expense ->

        }

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@ExpenseListActivity)
            adapter = this@ExpenseListActivity.adapter
            setHasFixedSize(false) // Important if items have variable heights
        }
    }

    private fun loadExpenses() {
        val userId = 1
        expenseViewModel.getExpensesByUser(userId).observe(this) { expenses ->
            Log.d("ExpenseList", "Loaded expenses: ${expenses.size}") // Check this log
            if (expenses.size > 1) {
                Log.d("ExpenseList", "First expense: ${expenses[0]}")
                Log.d("ExpenseList", "Second expense: ${expenses[1]}")
            }
            adapter.submitList(expenses)
        }

    }

}