package com.nadosunbae_android.app.presentation.ui.community.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nadosunbae_android.app.presentation.base.LoadableViewModel
import com.nadosunbae_android.data.datasource.database.entity.MajorList
import com.nadosunbae_android.domain.model.main.SelectableData
import com.nadosunbae_android.domain.model.major.MajorListData
import com.nadosunbae_android.domain.repository.major.MajorRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CommunityWriteViewModel @Inject constructor(
    private val majorRepository: MajorRepository
) : ViewModel(), LoadableViewModel {
    override val onLoadingEnd = MutableLiveData<Boolean>()

    //학과 변경 리스트
    private var _majorList = MutableLiveData<List<MajorListData>>()
    val majorList: LiveData<List<MajorListData>>
        get() = _majorList

    fun setMajorList(data : List<MajorListData>){
        _majorList.value = data
    }

    //학과 선택 내용
    private var _filter = MutableStateFlow(SelectableData.DEFAULT)
    val filter : Flow<SelectableData>
        get() = _filter

    fun setFilter(filter : SelectableData){
        _filter.value = filter
    }
}