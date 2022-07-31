package com.nadosunbae_android.app.presentation.ui.classroom.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ClassRoomMainContentViewModel @Inject constructor(
) : ViewModel() {

    val curFragment: MutableLiveData<Int>
        get() = ClassRoomMainContentViewModel.curFragment

    companion object {
        val curFragment = MutableLiveData(-1)
    }
}