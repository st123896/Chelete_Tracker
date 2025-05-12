package com.example.doon98.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.doon98.Models.BudgetGoalViewModel
import com.example.doon98.Models.ExpenseViewModel
import com.example.doon98.R
import com.example.doon98.RoomDb.BudgetGoal
import com.example.doon98.databinding.FragmentBudgetBinding
import java.util.Calendar


/**
 * Fragment for managing and displaying budget goals
 * Allows users to set and view their monthly budget limits
 */

class BudgetFragment : Fragment() {
    // View binding variables
    private var _binding: FragmentBudgetBinding? = null
    private val binding get() = _binding!!
    // ViewModels for budget and expense data

    private lateinit var budgetGoalViewModel: BudgetGoalViewModel
    private lateinit var expenseViewModel: ExpenseViewModel
    // Current user ID from shared preferences
    private var userId: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout using view binding
        _binding = FragmentBudgetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
// Get user ID from shared preferences
        userId = requireActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
            .getInt("user_id", 0)

        // Initialize ViewModels
        budgetGoalViewModel = ViewModelProvider(this)[BudgetGoalViewModel::class.java]
        expenseViewModel = ViewModelProvider(this)[ExpenseViewModel::class.java]

        // Load current budget and setup UI
        loadCurrentBudget()
        setupSaveButton()
    }
    /**
     * Loads the current month's budget goal for the user
     * Updates UI with the budget values if they exist
     */
    private fun loadCurrentBudget() {
        val calendar = Calendar.getInstance()
        val month = calendar.get(Calendar.MONTH) + 1
        val year = calendar.get(Calendar.YEAR)

        // Observe budget goal LiveData
        budgetGoalViewModel.getBudgetGoal(userId, month, year).observe(viewLifecycleOwner) { goal ->
            goal?.let {
                // Update UI with existing budget values
                binding.etMinBudget.setText(it.minAmount.toString())
                binding.etMaxBudget.setText(it.maxAmount.toString())
                updateSavedBudgetDisplay(it.minAmount, it.maxAmount)
                calculateBudgetProgress(it)
            } ?: run {
                // Handle case where no budget is set
                binding.tvSavedBudget.text = "Your Budget: Not set"
            }
        }
    }
    /**
     * Updates the display with the saved budget values
     * @param minAmount The minimum budget amount
     * @param maxAmount The maximum budget amount
     */
    private fun updateSavedBudgetDisplay(minAmount: Double, maxAmount: Double) {
        binding.tvSavedBudget.text =
            "Your Budget: $${"%.2f".format(minAmount)} - $${"%.2f".format(maxAmount)}"
    }
    /**
     * Calculates and displays budget progress based on current expenses
     * @param goal The budget goal containing min/max amounts
     */
    private fun calculateBudgetProgress(goal: BudgetGoal) {
        val calendar = Calendar.getInstance()
        val startOfMonth = calendar.apply {
            set(Calendar.DAY_OF_MONTH, 1)
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis

        val endOfMonth = calendar.apply {
            set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
            set(Calendar.HOUR_OF_DAY, 23)
            set(Calendar.MINUTE, 59)
            set(Calendar.SECOND, 59)
            set(Calendar.MILLISECOND, 999)
        }.timeInMillis

        expenseViewModel.getExpensesByDateRange(userId, startOfMonth, endOfMonth)
            .observe(viewLifecycleOwner) { expenses ->
                val totalSpent = expenses.sumOf { it.amount }
                val percentage = if (goal.maxAmount > 0) {
                    (totalSpent / goal.maxAmount * 100).toInt()
                } else {
                    0
                }

                val statusText = "Current spending: $${"%.2f".format(totalSpent)} " +
                        "($percentage% of budget)"
                binding.tvBudgetStatus.text = statusText
                binding.seekBarBudget.progress = percentage.coerceAtMost(100)
            }
    }
    /**
     * Sets up the save button click listener
     * Validates input and saves budget goals
     */
    private fun setupSaveButton() {
        binding.btnSaveBudget.setOnClickListener {
            val minText = binding.etMinBudget.text.toString()
            val maxText = binding.etMaxBudget.text.toString()

            if (minText.isEmpty() || maxText.isEmpty()) {
                Toast.makeText(requireContext(), "Please enter both values", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

            val minAmount = minText.toDouble()
            val maxAmount = maxText.toDouble()

            if (minAmount >= maxAmount) {
                Toast.makeText(requireContext(), "Max must be greater than min", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

            val calendar = Calendar.getInstance()
            val month = calendar.get(Calendar.MONTH) + 1
            val year = calendar.get(Calendar.YEAR)


            budgetGoalViewModel.getBudgetGoal(userId, month, year)
                .observe(viewLifecycleOwner) { existingGoal ->
                    if (existingGoal != null) {
                        existingGoal.minAmount = minAmount
                        existingGoal.maxAmount = maxAmount

                        budgetGoalViewModel.update(existingGoal)
                    } else {
                        val newGoal = BudgetGoal(
                            minAmount = minAmount,
                            maxAmount = maxAmount,
                            month = month,
                            year = year,
                            userId = userId,
                        )
                        budgetGoalViewModel.insert(newGoal)
                    }

                    // Update display and clear fields
                    updateSavedBudgetDisplay(minAmount, maxAmount)
                    binding.etMinBudget.text.clear()
                    binding.etMaxBudget.text.clear()
                    Toast.makeText(requireContext(), "Budget goals saved", Toast.LENGTH_SHORT)
                        .show()
                }
        }


}

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}