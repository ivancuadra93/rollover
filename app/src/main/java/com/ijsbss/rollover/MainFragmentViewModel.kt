package com.ijsbss.rollover

import androidx.databinding.Observable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ijsbss.rollover.data.db.CategoryRepository
import kotlinx.coroutines.launch

class MainFragmentViewModel(private val repository: CategoryRepository) : ViewModel(), Observable {

    val categories = repository.categories

    fun deleteCategory(categoryID: Int){
        delete(categoryID)
    }

    private fun delete(categoryID: Int){
        viewModelScope.launch {
            repository.delete(categoryID)
        }
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }
}