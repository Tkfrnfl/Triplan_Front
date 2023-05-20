package com.example.triplan.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SecondAskViewModel @Inject constructor() : ViewModel() {

    private val _inputData = MutableLiveData<String>()
    val inputData: LiveData<String> get() = _inputData

    fun updateData(data: String) {
        _inputData.value = data
    }
}