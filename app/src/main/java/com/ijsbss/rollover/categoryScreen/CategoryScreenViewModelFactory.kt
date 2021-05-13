package com.ijsbss.rollover.categoryScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ijsbss.rollover.data.db.CategoryRepository
import java.lang.IllegalArgumentException

class CategoryScreenViewModelFactory(private val repository: CategoryRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(CategoryScreenFragmentViewModel::class.java)) {
            return CategoryScreenFragmentViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }
}
