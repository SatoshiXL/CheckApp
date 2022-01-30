package com.example.healthrecorder.fragment.list.detail.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class InfoViewModel : ViewModel() {

    val dataset = MutableLiveData<String>()
    val updatedDataset = MutableLiveData<Int>()

    init {
        Log.d("TAG", "${dataset.value}")
    }
}
