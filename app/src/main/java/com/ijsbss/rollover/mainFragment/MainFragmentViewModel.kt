package com.ijsbss.rollover.mainFragment

import android.content.Context
import android.widget.Toast
import androidx.databinding.Observable
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ijsbss.rollover.data.db.CategoryRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class MainFragmentViewModel(private val repository: CategoryRepository) : ViewModel(), Observable {

    val categories = repository.categories

    fun deleteCategoryAndExpense(categoryID: Int){
        delete(categoryID)
    }

    private fun delete(categoryID: Int){
        viewModelScope.launch {
            repository.deleteCategoryAndExpense(categoryID)
        }
    }

    fun updateTotalSpentToZero(mainFragment: LifecycleOwner, context: Context?){
        update(mainFragment, context)
    }

    private fun update(mainFragment: LifecycleOwner, context: Context?) {
        categories.observe(mainFragment, {
            viewModelScope.launch {
                var i = 0
                val indices = it.size

                while(i < indices) {

                    val numberOfDays = ( (ChronoUnit.DAYS.between(LocalDate.parse(it[i].date, DateTimeFormatter.ISO_DATE), LocalDate.now() )).rem(it[i].rolloverPeriod) )
                    if( (numberOfDays.toInt() == 0) && ( LocalDate.parse(it[i].date, DateTimeFormatter.ISO_DATE) != LocalDate.now()) && (it[i].totalSpent != 0.0F) ) {
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