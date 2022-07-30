package com.nadosunbae_android.app.presentation.ui.classroom.viewmodel

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ClassRoomMainContentViewModel @Inject constructor(
) : ViewModel() {

    val curFragmentField: ObservableField<Int>
        get() = ClassRoomMainContentViewModel.curFragmentField

    val curFragmentData: MutableLiveData<Int>
        get() = ClassRoomMainContentViewModel.curFragmentData

    companion object {
        val curFragmentField = ObservableField(-1)
        val curFragmentData = MutableLiveData(-1)
    }
}