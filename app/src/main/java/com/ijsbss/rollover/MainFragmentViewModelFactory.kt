package com.ijsbss.rollover

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ijsbss.rollover.data.db.CategoryRepository
import java.lang.IllegalArgumentException

class MainFragmentViewModelFactory(private val repository: CategoryRepository) :ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MainFragmentViewModel::class.java)) {
            return MainFragmentViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }
}