package com.example.doon98

import android.R
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProvider
import com.example.doon98.Models.CategoryViewModel
import com.example.doon98.Models.ExpenseViewModel
import com.example.doon98.RoomDb.Expense
import com.example.doon98.databinding.ActivityAddExpenseBinding
import java.io.File
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


class AddExpenseActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddExpenseBinding
    private lateinit var expenseViewModel: ExpenseViewModel
    private lateinit var categoryViewModel: CategoryViewModel
    private var selectedDate: Calendar = Calendar.getInstance()
    private var photoPath: String? = null
    private var userId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddExpenseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userId = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
            .getInt("user_id", 0)

        expenseViewModel = ViewModelProvider(this)[ExpenseViewModel::class.java]
        categoryViewModel = ViewModelProvider(this)[CategoryViewModel::class.java]

        setupCategorySpinner()
        setupDatePicker()
        setupPhotoButton()

        binding.btnSaveExpense.setOnClickListener {
            saveExpense()
        }
    }

    private fun setupCategorySpinner() {
        categoryViewModel.getCategoriesByUser(userId).observe(this) { categories ->
            val adapter = ArrayAdapter(
                this,
                R.layout.simple_spinner_item,
                categories.map { it.name }
            )
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerCategory.adapter = adapter
        }
    }

    private fun setupDatePicker() {
        binding.btnSelectDate.setOnClickListener {
            DatePickerDialog(
                this,
                { _, year, month, day ->
                    selectedDate.set(year, month, day)
                    binding.tvSelectedDate.text = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
                        .format(selectedDate.time)
                },
                selectedDate.get(Calendar.YEAR),
                selectedDate.get(Calendar.MONTH),
                selectedDate.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }

    private fun setupPhotoButton() {
        binding.btnAddPhoto.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if (intent.resolveActivity(packageManager) != null) {
                try {
                    val photoFile = createImageFile()
                    photoFile?.let {
                        val photoURI = FileProvider.getUriForFile(
                            this,
                            "${packageName}.fileprovider", // Fixed: removed space
                            it
                        )
                        photoPath = it.absolutePath
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
                    }
                } catch (ex: Exception) {
                    Toast.makeText(this, "Error creating file: ${ex.message}", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "No camera app found", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            photoPath?.let { path ->
                val bitmap = BitmapFactory.decodeFile(path)
                binding.ivExpensePhoto.setImageBitmap(bitmap)
                binding.ivExpensePhoto.visibility = View.VISIBLE
            }
        }
    }

    private fun createImageFile(): File? {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date()) // Fixed: changed HMS to HHmmss
        val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        ).apply {
            // Create parent directories if they don't exist
            parentFile?.mkdirs()
        }
    }

    private fun saveExpense() {
        val amountText = binding.etAmount.text.toString()
        val description = binding.etDescription.text.toString()
        val categoryPosition = binding.spinnerCategory.selectedItemPosition

        if (amountText.isEmpty() || description.isEmpty() || categoryPosition == -1) {
            Toast.makeText(this, "Please fill all required fields", Toast.LENGTH_SHORT).show()
            return
        }

        val amount = amountText.toDoubleOrNull() ?: 0.0
        categoryViewModel.getCategoriesByUser(userId).observe(this) { categories ->
            if (categories.isNotEmpty() && categoryPosition < categories.size) {
                val category = categories[categoryPosition]
                val expense = Expense(
                    amount = amount,
                    date = selectedDate.timeInMillis,
                    description = description,
                    categoryId = category.id,
                    userId = userId,
                    photoPath = photoPath
                )

                expenseViewModel.insert(expense)
                Toast.makeText(this, "Expense saved", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    companion object {
        private const val REQUEST_IMAGE_CAPTURE = 1
    }

}