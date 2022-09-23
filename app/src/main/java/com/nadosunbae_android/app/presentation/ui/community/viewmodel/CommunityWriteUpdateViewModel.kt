package com.nadosunbae_android.app.presentation.ui.community.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nadosunbae_android.domain.model.community.CommunityWriteUpdateData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class CommunityWriteUpdateViewModel @Inject constructor() : ViewModel() {

    private var _initUpdateData = MutableStateFlow(CommunityWriteUpdateData.DEFAULT)
    val initUpdateData : StateFlow<CommunityWriteUpdateData>
        get() = _initUpdateData

    fun setUpdateData(data : CommunityWriteUpdateData){
        updateTitle.value = data.title
        updateContent.value = data.content
        _initUpdateData.value = data
    }

    //수정된 제목
    var updateTitle = MutableLiveData<String>()
    var updateContent = MutableLiveData<String>()
}