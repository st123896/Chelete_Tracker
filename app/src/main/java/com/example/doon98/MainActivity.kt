package com.example.doon98

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import com.example.doon98.databinding.ActivityMainBinding
import com.example.doon98.ui.BudgetFragment
import com.example.doon98.ui.CategoriesFragment
import com.example.doon98.ui.ExpensesFragment
import com.google.android.material.navigation.NavigationView
/**
 * Main container activity with navigation drawer
 * - Hosts fragments for different features (Expenses, Categories, Budget)
 * - Manages navigation between fragments
 * - Handles logout functionality
 * - Supports deep linking to specific fragments
 */
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize views
        drawerLayout = binding.drawerLayout
        navView = binding.navView

        setSupportActionBar(binding.toolbar)

        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.nav_expenses, R.id.nav_categories, R.id.nav_budget),
            drawerLayout
        )

        setupNavigation()

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, ExpensesFragment())
                .commit()
        }
        // Check if we need to open a specific fragment
        when (intent?.getStringExtra("open_fragment")) {
            "categories" -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, CategoriesFragment())
                    .commit()
            }

            "budget" -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, BudgetFragment())
                    .commit()
            }

            else -> {
                // Default fragment
                if (savedInstanceState == null) {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer, ExpensesFragment())
                        .commit()
                }
            }
        }
    }

    private fun setupNavigation() {
        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_expenses -> {
                    // Navigate to AddExpenseActivity
                    startActivity(Intent(this, AddExpenseActivity::class.java))
                }
                R.id.nav_categories -> {
                    // Replace with CategoriesFragment
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer, CategoriesFragment())
                        .addToBackStack(null) // Optional: Add to back stack
                        .commit()
                }
                R.id.nav_budget -> {
                    // Replace with BudgetFragment
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer, BudgetFragment())
                        .addToBackStack(null) // Optional: Add to back stack
                        .commit()
                }
                R.id.nav_logout -> {
                    // Clear shared preferences
                    getSharedPreferences("user_prefs", Context.MODE_PRIVATE).edit().clear().apply()
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                }
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
    }

    // Optional: Handle back button with navigation drawer
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.fragmentContainer)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}