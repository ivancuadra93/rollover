package com.ijsbss.rollover.recyclerViews

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ijsbss.rollover.R
import com.ijsbss.rollover.data.relations.CategoryWithExpenses
import com.ijsbss.rollover.data.entities.Expense
import com.ijsbss.rollover.databinding.ExpenseListItemBinding
import java.text.DecimalFormat

class ExpenseRecyclerViewAdapter(private val expensesList: MutableList<CategoryWithExpenses>) : RecyclerView.Adapter<ExpenseRecyclerViewAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ExpenseListItemBinding = DataBindingUtil.inflate(layoutInflater, R.layout.expense_list_item, parent, false)

        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(expensesList[0].expenses[position])
    }

    override fun getItemCount(): Int {
        return expensesList[0].expenses.size
    }

    inner class MyViewHolder(private val binding: ExpenseListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(expense: Expense){

            val decimalFormat = DecimalFormat("0.00")
            val amountWithTwoDecimalFormat = decimalFormat.format(expense.amount)
            val dollarSignAmount = "$$amountWithTwoDecimalFormat"

            binding.expenseNameView.text = expense.name
            binding.expenseAmountView.text = dollarSignAmount
            binding.dateView.text = expense.date
            binding.cardView.setCardBackgroundColor(expensesList[0].category.color)
            binding.accountView.text = expense.account
        }
    }
}
