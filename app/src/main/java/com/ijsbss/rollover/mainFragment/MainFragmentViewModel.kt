package com.ijsbss.rollover.mainFragment

import androidx.databinding.Observable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ijsbss.rollover.data.db.CategoryRepository
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

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

    fun updateTotalSpentToZero(mainFragment: MainFragment) {
        categories.observe(mainFragment.viewLifecycleOwner, {
            viewModelScope.launch {
                var i = 0
                val indices = it.size

                while(i < indices) {

                    it[i]

                    val numberOfDays = ((ChronoUnit.DAYS.between(LocalDate.parse(it[i].date, DateTimeFormatter.ISO_DATE), LocalDate.now() )).rem(it[i].rolloverPeriod!!))

                    if(numberOfDays.toInt() == 0) {
                        repository.updateTotalSpentToZero(it[i].categoryId)
                    }

                    i++
                }
            }
        })
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }
}