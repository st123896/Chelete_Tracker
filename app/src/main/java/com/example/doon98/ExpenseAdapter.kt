package com.example.doon98

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.doon98.RoomDb.Expense
import com.example.doon98.databinding.ItemExpenseBinding
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class ExpenseAdapter(private val onClick: (Expense) -> Unit) :
    ListAdapter<Expense, ExpenseAdapter.ExpenseViewHolder>(ExpenseDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        val binding = ItemExpenseBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ExpenseViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ExpenseViewHolder(
        private val binding: ItemExpenseBinding,
        private val onClick: (Expense) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(expense: Expense) {
            binding.tvAmount.text = "$${expense.amount}"
            binding.tvDescription.text = expense.description
            binding.tvDate.text = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
                .format(Date(expense.date))

            // You would need to fetch category name from database here
            // binding.tvCategory.text = categoryName

            expense.photoPath?.let { path ->
                binding.ivPhoto.visibility = View.VISIBLE
                Glide.with(itemView.context)
                    .load(File(path))
                    .into(binding.ivPhoto)
            } ?: run {
                binding.ivPhoto.visibility = View.GONE
            }

            binding.root.setOnClickListener { onClick(expense) }
        }
    }

    class ExpenseDiffCallback : DiffUtil.ItemCallback<Expense>() {
        override fun areItemsTheSame(oldItem: Expense, newItem: Expense): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Expense, newItem: Expense): Boolean {
            val isSame = oldItem == newItem
            if (!isSame) {
                Log.d("DiffDebug", "Items differ: $oldItem vs $newItem")
            }
            return isSame
        }
    }
}