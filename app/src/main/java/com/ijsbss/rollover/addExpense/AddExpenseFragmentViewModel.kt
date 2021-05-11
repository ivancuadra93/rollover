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
import java.util.*

class AddExpenseFragmentViewModel(private val repository: ExpenseRepository) : ViewModel(), Observable{

    @Bindable
    val inputName = MutableLiveData<String>()

    @Bindable
    val inputAmount = MutableLiveData<String>()

    @Bindable
    val inputCreditOrDebit = MutableLiveData<String>()

    @Bindable
    var creditButtonColor = MutableLiveData<Boolean>()

    @Bindable
    val debitButtonColor = MutableLiveData<Boolean>()

    fun chooseCredit(){
        inputCreditOrDebit.value = "Credit"
        creditButtonColor.value = true
        debitButtonColor.value = false
    }

    fun chooseDebit(){
        inputCreditOrDebit.value = "Debit"
        debitButtonColor.value = true
        creditButtonColor.value = false

    }


    fun inputExpense(categoryId: Int, totalSpent: Float){
        if( (inputName.value != null) && (inputAmount.value != null) && (inputCreditOrDebit.value != null) ){
            val name = inputName.value!!.toUpperCase(Locale.ROOT)
            val amount = inputAmount.value!!.toFloat()
            val account = inputCreditOrDebit.value!!

            val newTotalSpent: Float = amount + totalSpent

            insert( Expense(0,name, amount, account, LocalDate.now().toString(),0,  categoryId  ))
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
            repository.updateTotalSpent(categoryId, newTotalSpent)
        }
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }
}