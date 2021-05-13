package com.ijsbss.rollover.categoryScreen

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ijsbss.rollover.data.Relations.CategoryWithExpenses
import com.ijsbss.rollover.data.db.CategoryRepository
import com.ijsbss.rollover.data.entities.Category
import com.ijsbss.rollover.data.entities.Expense
import kotlinx.coroutines.launch

class CategoryScreenFragmentViewModel(private val repository: CategoryRepository): ViewModel(), Observable {

    fun expenses(categoryId : Int) : LiveData<MutableList<CategoryWithExpenses>> {
        return repository.categoryWithExpenses(categoryId)
    }

    fun updateTotalSpentToZero(categoryId: Int) : Float{
        viewModelScope.launch {
            repository.updateTotalSpentToZero(categoryId)
        }
        return 0.0F
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

}