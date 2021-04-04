package com.ijsbss.rollover

import androidx.databinding.Bindable
import androidx.lifecycle.MutableLiveData
import androidx.databinding.Observable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ijsbss.rollover.data.db.CategoryRepository
import com.ijsbss.rollover.data.entities.Category
import kotlinx.coroutines.launch

class FirstFragmentViewModel(private val repository: CategoryRepository) : ViewModel(), Observable {

    val categories = repository.categories

    @Bindable
    val firstFragmentText = MutableLiveData<String>()

    @Bindable
    val nextButtonText = MutableLiveData<String>()

    @Bindable
    val inputName = MutableLiveData<String>()

    //the following is commented out in case we need it later
//    @Bindable
//    val inputExpectation = MutableLiveData<Float>()
//
//    @Bindable
//    val inputTotalSpent = MutableLiveData<Float>()
//
//    @Bindable
//    val inputRolloverPeriod = MutableLiveData<Byte>()
//
//    @Bindable
//    val inputColor = MutableLiveData<String>()
//
//    @Bindable
//    val inputThreshold = MutableLiveData<Float>()

    @Bindable
    val saveOrUpdateButtonText = MutableLiveData<String>()

    @Bindable
    val clearAllOrDeleteButtonText = MutableLiveData<String>()

    init{
        firstFragmentText.value = "First Fragment Here"
        nextButtonText.value = "Next"
        saveOrUpdateButtonText.value = "Save"
        clearAllOrDeleteButtonText.value ="Clear All"
    }

    fun saveOrUpdate(){
        val name = inputName.value!!
        //the following is commented out in case we need it later

//        val expectation = inputExpectation.value!!
//        val totalSpent = inputTotalSpent.value!!
//        val rolloverPeriod = inputRolloverPeriod.value!!
//        val color = inputColor.value!!
//        val threshold = inputThreshold.value!!

        insert(Category(0, name, 0.0F, 0.0F, 0, "test", 0.0F, 0, 0))

        inputName.value = null
        //the following is commented out in case we need it later

//        inputExpectation.value = null
//        inputTotalSpent.value = null
//        inputRolloverPeriod.value = null
//        inputColor.value = null
//        inputThreshold.value = null
    }

    fun clearAllOrDelete(){
        clearAll()
    }

    fun insert(category: Category){
        viewModelScope.launch {
            repository.insert(category)
        }
    }

    fun clearAll(){
        viewModelScope.launch {
            repository.deleteAll()
        }
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }
}