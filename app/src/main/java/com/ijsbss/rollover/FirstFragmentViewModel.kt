package com.ijsbss.rollover

import androidx.databinding.Bindable
import androidx.lifecycle.MutableLiveData
import androidx.databinding.Observable
import androidx.lifecycle.ViewModel

class FirstFragmentViewModel() : ViewModel(), Observable {

    @Bindable
    val firstFragmentText = MutableLiveData<String>()

    @Bindable
    val nextButtonText = MutableLiveData<String>()

    init{
        firstFragmentText.value = "First Fragment Here"
        nextButtonText.value = "Next"
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }
}