package com.ijsbss.rollover

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ijsbss.rollover.data.db.CategoryRepository
import java.lang.IllegalArgumentException

class FirstFragmentViewModelFactory(private val repository: CategoryRepository) :ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(FirstFragmentViewModel::class.java)) {
            return FirstFragmentViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }
}