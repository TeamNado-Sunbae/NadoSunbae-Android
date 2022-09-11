package com.nadosunbae_android.app.presentation.ui.community.viewmodel

import androidx.lifecycle.*
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.presentation.base.LoadableViewModel
import com.nadosunbae_android.data.datasource.database.entity.MajorList
import com.nadosunbae_android.domain.model.main.SelectableData
import com.nadosunbae_android.domain.model.major.MajorListData
import com.nadosunbae_android.domain.model.post.PostWriteParam
import com.nadosunbae_android.domain.repository.major.MajorRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*
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

    fun setMajorList(data: List<MajorListData>) {
        _majorList.value = data
    }

    //학과 선택 내용
    private var _filter = MutableStateFlow(SelectableData.DEFAULT)
    val filter: Flow<SelectableData>
        get() = _filter

    fun setFilter(filter: SelectableData) {
        _filter.value = filter
    }

    //게시글 작성 parameter
    var writeParam = MutableStateFlow(PostWriteParam.DEFAULT)

    //완료 체크
    var writeTitle = MutableStateFlow("")
    var writeContent = MutableStateFlow("")

    private var _completeButton = MutableStateFlow(false)
    val completeButton : StateFlow<Boolean>
        get() = _completeButton

    fun setCompleteButton() {
        viewModelScope.launch {
            combine(writeTitle, writeContent) { title, content ->
                title.isNotEmpty() && content.isNotEmpty()
            }.collectLatest {
                _completeButton.value = it
            }

        }
    }

    //parameter 깊은 복사를 통한 데이터 변화 탐색
    fun setNewWriteParam(targetNum: Boolean , newItem: String) {
        writeParam.value.apply {
            val newParam = when (targetNum) {
                true -> copy(title = newItem)
                false -> copy(content = newItem)
            }
            writeParam.value = newParam
        }
    }


}