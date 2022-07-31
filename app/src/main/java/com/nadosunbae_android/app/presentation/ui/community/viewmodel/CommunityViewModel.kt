package com.nadosunbae_android.app.presentation.ui.community.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CommunityViewModel @Inject constructor(): ViewModel() {

    var filterMajor = MutableLiveData("학과")
}