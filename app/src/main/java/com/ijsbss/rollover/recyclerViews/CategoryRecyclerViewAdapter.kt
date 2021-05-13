package com.ijsbss.rollover.recyclerViews

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.ijsbss.rollover.mainFragment.MainFragmentViewModel
import com.ijsbss.rollover.R
import com.ijsbss.rollover.data.entities.Category
import com.ijsbss.rollover.databinding.ListItemBinding
import java.text.DecimalFormat

class CategoryRecyclerViewAdapter(private val categoriesList: MutableList<Category>, private val mainFragmentViewModel: MainFragmentViewModel) : RecyclerView.Adapter<CategoryRecyclerViewAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ListItemBinding = DataBindingUtil.inflate(layoutInflater, R.layout.list_item, parent, false)

        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(categoriesList[position])

    }

    override fun getItemCount(): Int {
        return categoriesList.size
    }

    inner class MyViewHolder(private val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root), View.OnClickListener, View.OnLongClickListener {
        private var itemID = 0

        init {
            binding.root.findViewById<Button>(R.id.delete_button).setOnClickListener(this)
            binding.root.findViewById<Button>(R.id.edit_button).setOnClickListener(this)
            binding.root.findViewById<androidx.cardview.widget.CardView>(R.id.card_view).setOnClickListener(this)
            binding.root.findViewById<androidx.cardview.widget.CardView>(R.id.card_view).setOnLongClickListener(this)
        }

        fun bind(category: Category) {
            val decimalFormat = DecimalFormat("0.00")
            val totalSpentWithTwoDecimalFormat = decimalFormat.format(category.totalSpent)
            val dollarSignTotalSpent = "$$totalSpentWithTwoDecimalFormat"

            binding.nameTextView.text = category.name
            binding.totalSpentView.text = dollarSignTotalSpent
            binding.cardView.setCardBackgroundColor(category.color)
            itemID = category.categoryId

        }

        override fun onClick(v: View?) {
            when (v?.id) {
                R.id.delete_button -> {

                    Toast.makeText(v.context, "Deleted " + binding.nameTextView.text, Toast.LENGTH_LONG).show()

                    mainFragmentViewModel.deleteCategoryAndExpense(itemID)
                }

                R.id.edit_button -> {

                    val bundle = bundleOf(
                        ("categoryId" to itemID),
                        ("categoryName" to categoriesList[adapterPosition].name),
                        ("expectation" to categoriesList[adapterPosition].expectation),
                        ("totalSpent" to categoriesList[adapterPosition].totalSpent),
                        ("rollover" to categoriesList[adapterPosition].rolloverPeriod),
                        ("categoryColor" to categoriesList[adapterPosition].color),
                        ("threshold" to categoriesList[adapterPosition].threshold),
                        ("date" to categoriesList[adapterPosition].date)
                    )

                    v.findNavController().navigate(R.id.action_MainFragment_to_AddCategoryFragment, bundle)
                }

                R.id.card_view -> {

                    val bundle = bundleOf(
                            ("categoryId" to itemID),
                            ("categoryName" to categoriesList[adapterPosition].name),
                            ("categoryColor" to categoriesList[adapterPosition].color),
                            ("totalSpent" to categoriesList[adapterPosition].totalSpent),
                            ("expectation" to categoriesList[adapterPosition].expectation),
                            ("rolloverPeriod" to categoriesList[adapterPosition].rolloverPeriod),
                            ("date" to categoriesList[adapterPosition].date)
                    )

                    v.findNavController().navigate(R.id.action_MainFragment_to_CategoryScreenFragment, bundle)
                }
            }
        }

        override fun onLongClick(v: View?): Boolean {
            binding.editAndDeleteLayout.isVisible = true

            return true
        }
    }
}