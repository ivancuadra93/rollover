package com.ijsbss.rollover

import androidx.databinding.Observable
import androidx.lifecycle.ViewModel
import com.ijsbss.rollover.data.db.CategoryRepository

class MainFragmentViewModel(private val repository: CategoryRepository) : ViewModel(), Observable {

    val categories = repository.categories

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }
}