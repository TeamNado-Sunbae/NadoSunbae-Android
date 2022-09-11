package com.nadosunbae_android.app.presentation.ui.community.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nadosunbae_android.app.presentation.base.LoadableViewModel
import com.nadosunbae_android.app.presentation.ui.main.MainGlobals
import com.nadosunbae_android.domain.model.main.SelectableData
import com.nadosunbae_android.domain.model.major.MajorListData
import com.nadosunbae_android.domain.model.post.PostWriteData
import com.nadosunbae_android.domain.model.post.PostWriteParam
import com.nadosunbae_android.domain.repository.major.MajorRepository
import com.nadosunbae_android.domain.repository.post.PostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CommunityWriteViewModel @Inject constructor(
    private val postRepository: PostRepository
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
    val filter: StateFlow<SelectableData>
        get() = _filter

    fun setFilter(filter: SelectableData) {
        _filter.value = filter
    }

    //완료 체크
    var writeTitle = MutableStateFlow("")
    var writeContent = MutableStateFlow("")

    private var _completeButton = MutableStateFlow(false)
    val completeButton: StateFlow<Boolean>
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

    //게시글 작성 서버
    private var _postWrite = MutableStateFlow(PostWriteData.DEFAULT)
    val postWrite : StateFlow<PostWriteData>
        get() = _postWrite

    fun postWrite(type: () -> String, title: String, content: String) {
        viewModelScope.launch {
            postRepository.postWrite(
                PostWriteParam(
                    type = type(),
                    majorId = filter.value.id.toString(),
                    answerId = MainGlobals.signInData?.userId.toString(),
                    title = title,
                    content = content
                )
            ).catch {
                Timber.d("작성 실패")
            }.collectLatest {
                Timber.d("작성 성공")
                _postWrite.value = it
            }
        }

    }

}