package com.nadosunbae_android.app.presentation.ui.community.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nadosunbae_android.domain.model.community.CommunityWriteUpdateData
import com.nadosunbae_android.domain.repository.post.PostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class CommunityWriteUpdateViewModel @Inject constructor(
    private val postRepository: PostRepository
) : ViewModel() {

    private var _initUpdateData = MutableStateFlow(CommunityWriteUpdateData.DEFAULT)
    val initUpdateData: StateFlow<CommunityWriteUpdateData>
        get() = _initUpdateData

    fun setUpdateData(data: CommunityWriteUpdateData) {
        updateTitle.value = data.title
        updateContent.value = data.content
        _initUpdateData.value = data
    }

    //수정된 제목
    var updateTitle = MutableLiveData<String>()
    var updateContent = MutableLiveData<String>()

    var checkComplete = MediatorLiveData<Boolean>().apply {
        this.addSource(updateContent) {
            this.value = setCheckComplete()
        }
        this.addSource(updateTitle) {
            this.value = setCheckComplete()
        }
    }


    private fun setCheckComplete(): Boolean {
        return updateTitle.value != initUpdateData.value.title || updateContent.value != initUpdateData.value.content
    }

}