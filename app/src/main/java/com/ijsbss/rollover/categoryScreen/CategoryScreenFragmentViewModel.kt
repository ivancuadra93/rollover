package com.ijsbss.rollover.categoryScreen

import androidx.databinding.Observable
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ijsbss.rollover.data.Relations.CategoryWithExpenses
import com.ijsbss.rollover.data.db.CategoryRepository
import com.ijsbss.rollover.data.entities.Expense

class CategoryScreenFragmentViewModel(private val repository: CategoryRepository): ViewModel(), Observable {

    fun expenses(categoryId : Int) : LiveData<MutableList<CategoryWithExpenses>> {
        return repository.categoryWithExpenses(categoryId)
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

}