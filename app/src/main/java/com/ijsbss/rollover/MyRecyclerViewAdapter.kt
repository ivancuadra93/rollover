package com.ijsbss.rollover

import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ijsbss.rollover.data.entities.Category
import com.ijsbss.rollover.databinding.ListItemBinding

class MyRecyclerViewAdapter(private val categoriesList: MutableList<Category>, private val mainFragmentViewModel: MainFragmentViewModel ) : RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder>() {

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


//    fun delete(position: Int) {
//        categoriesList.removeAt(position)
//        notifyItemRemoved(position)
//    }


   inner class MyViewHolder(private val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root), View.OnClickListener, View.OnLongClickListener {
        private var itemID = 0

        init {
            binding.root.findViewById<Button>(R.id.delete_button).setOnClickListener(this)
            binding.root.findViewById<androidx.cardview.widget.CardView>(R.id.card_view).setOnClickListener(this)
            binding.root.findViewById<androidx.cardview.widget.CardView>(R.id.card_view).setOnLongClickListener(this)
        }

        fun bind(category: Category) {
            val dollarSignTotalSpent = "$" + category.totalSpent.toString()

            binding.nameTextView.text = category.name
            binding.totalSpentView.text = dollarSignTotalSpent
            binding.cardView.setCardBackgroundColor(category.color)
            itemID = category.id
        }

        override fun onClick(v: View?) {
            when (v?.id) {
                R.id.delete_button -> {
                    Toast.makeText(v.context, itemID.toString() + " " + binding.nameTextView.text, Toast.LENGTH_SHORT).show()
                    //delete(adapterPosition)
                    mainFragmentViewModel.deleteCategory(itemID)
                }

                R.id.card_view -> {
                    Toast.makeText(v.context, "Inside Click", Toast.LENGTH_SHORT).show()
                    binding.editAndDeleteLayout.visibility = GONE

                }
            }
        }

        override fun onLongClick(v: View?): Boolean {
            Toast.makeText(v?.context, "long click", Toast.LENGTH_SHORT).show()
            binding.editAndDeleteLayout.isVisible = true

            return true
        }
    }
}
