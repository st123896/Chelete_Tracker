package com.example.doon98.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.doon98.CategoryAdapter
import com.example.doon98.Models.CategoryViewModel
import com.example.doon98.R
import com.example.doon98.RoomDb.Category
import com.example.doon98.databinding.FragmentCategoriesBinding
/**
 * Fragment for managing expense categories
 * Allows adding, viewing and deleting categories
 */
class CategoriesFragment : Fragment() {
    // View binding variables
    private var _binding: FragmentCategoriesBinding? = null
    private val binding get() = _binding!!
    // ViewModel for category data
    private lateinit var categoryViewModel: CategoryViewModel
    private lateinit var adapter: CategoryAdapter

    // RecyclerView adapter
    private var userId: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userId = requireActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
            .getInt("user_id", 0)

        categoryViewModel = ViewModelProvider(this)[CategoryViewModel::class.java]

        adapter = CategoryAdapter { category ->
            showDeleteDialog(category)
        }

        binding.rvCategories.layoutManager = LinearLayoutManager(requireContext())
        binding.rvCategories.adapter = adapter

        binding.btnAddCategory.setOnClickListener {
            showAddCategoryDialog()
        }

        loadCategories()
    }

    private fun loadCategories() {
        categoryViewModel.getCategoriesByUser(userId).observe(viewLifecycleOwner) { categories ->
            adapter.submitList(categories)
        }
    }

    private fun showAddCategoryDialog() {
        val editText = EditText(requireContext())
        editText.hint = "Category name"

        AlertDialog.Builder(requireContext())
            .setTitle("Add New Category")
            .setView(editText)
            .setPositiveButton("Add") { _, _ ->
                val name = editText.text.toString()
                if (name.isNotEmpty()) {
                    val category = Category(name = name, userId = userId)
                    categoryViewModel.insert(category)
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showDeleteDialog(category: Category) {
        AlertDialog.Builder(requireContext())
            .setTitle("Delete Category")
            .setMessage("Are you sure you want to delete ${category.name}?")
            .setPositiveButton("Delete") { _, _ ->
                categoryViewModel.delete(category)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}