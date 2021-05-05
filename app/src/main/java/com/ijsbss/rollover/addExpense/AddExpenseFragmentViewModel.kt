package com.ijsbss.rollover.addExpense

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ijsbss.rollover.data.db.ExpenseRepository
import com.ijsbss.rollover.data.entities.Expense
import kotlinx.coroutines.launch
import java.time.LocalDate

class AddExpenseFragmentViewModel(private val repository: ExpenseRepository) : ViewModel(), Observable{

    @Bindable
    val inputName = MutableLiveData<String>()

    @Bindable
    val inputAmount = MutableLiveData<String>()

    fun inputExpense(categoryId: Int, totalSpent: Float){
        if(inputName.value != null && inputAmount.value != null){
            val name = inputName.value!!.toUpperCase()
            val amount = inputAmount.value!!.toFloat()

            val newTotalSpent: Float = amount + totalSpent

            insert( Expense(0,name, amount, 0, LocalDate.now().toString(),0,  categoryId  ))
            update(categoryId, newTotalSpent)

            inputName.value = null
            inputAmount.value = null
        }
    }

    private fun insert(expense: Expense){
        viewModelScope.launch {
            repository.insert(expense)
        }
    }

    private fun update(categoryId: Int, newTotalSpent: Float){
        viewModelScope.launch {
            repository.update(categoryId, newTotalSpent)
        }
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }
}