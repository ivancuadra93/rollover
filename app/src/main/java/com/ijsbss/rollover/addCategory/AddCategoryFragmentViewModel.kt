package com.ijsbss.rollover.addCategory

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ijsbss.rollover.data.db.CategoryRepository
import com.ijsbss.rollover.data.entities.Category
import com.ijsbss.rollover.utilities.*
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.*

class AddCategoryFragmentViewModel(private val repository: CategoryRepository) : ViewModel(), Observable {

    @Bindable
    val inputName = MutableLiveData<String>()

    @Bindable
    val inputExpectation = MutableLiveData<String>()

    @Bindable
    val inputRolloverPeriod = MutableLiveData<String>()

    @Bindable
    val inputColor = MutableLiveData<String>()

    @Bindable
    val inputThreshold = MutableLiveData<String>()

    fun inputCategory(updating: Boolean, categoryId: Int, totalSpent: Float, date: String){
        if(inputName.value != null && inputExpectation.value != null && inputRolloverPeriod.value != null && inputColor.value != null && inputThreshold.value != null){
            val name = inputName.value!!
            val expectation = inputExpectation.value!!
            val rolloverPeriod = inputRolloverPeriod.value!!
            val color = colorConverter(inputColor)
            val threshold = inputThreshold.value!!


            if (updating) {
                insert(Category(0, name.toUpperCase(Locale.ROOT), expectation.toFloat(), totalSpent, rolloverPeriod.toByte(), color, threshold.toFloat(), date, 0))
                delete(categoryId)
                updateCategoryId(categoryId)
            }
            else{
                insert(Category(0, name.toUpperCase(Locale.ROOT), expectation.toFloat(), 0.00F, rolloverPeriod.toByte(), color, threshold.toFloat(), LocalDate.now().toString(), 0))
            }

            inputName.value = null
            inputExpectation.value = null
            inputRolloverPeriod.value = null
            inputColor.value = null
            inputThreshold.value = null
        }
    }

    private fun colorConverter(color: MutableLiveData<String>): Int {
        return when(color.value){
            "Red" -> RED
            "Orange" -> ORANGE
            "Yellow" -> YELLOW
            "Green" -> GREEN
            "Blue" -> BLUE
            "Purple" -> PURPLE
            else -> {
                RED
            }
        }
    }

    private fun insert(category: Category){
        viewModelScope.launch {
            repository.insert(category)
        }
    }

    private fun delete(categoryId: Int){
        viewModelScope.launch {
            repository.deleteCategoryOnly(categoryId)
        }
    }

    private fun updateCategoryId(categoryId: Int){
        viewModelScope.launch {
            repository.updateCategoryId(categoryId)
        }
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }
}