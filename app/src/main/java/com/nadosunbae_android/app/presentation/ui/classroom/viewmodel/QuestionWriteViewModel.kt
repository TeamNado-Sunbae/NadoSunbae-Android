package com.nadosunbae_android.app.presentation.ui.classroom.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nadosunbae_android.app.presentation.base.LoadableViewModel
import com.nadosunbae_android.domain.model.classroom.ClassRoomPostWriteData
import com.nadosunbae_android.domain.model.classroom.WriteUpdateData
import com.nadosunbae_android.domain.model.classroom.WriteUpdateItem
import com.nadosunbae_android.domain.model.post.PostWriteData
import com.nadosunbae_android.domain.model.post.PostWriteParam
import com.nadosunbae_android.domain.repository.post.PostRepository
import com.nadosunbae_android.domain.usecase.classroom.PutWriteUpdateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class QuestionWriteViewModel @Inject constructor(
    val postRepository: PostRepository,
    val putWriteUpdateUseCase: PutWriteUpdateUseCase
) : ViewModel(), LoadableViewModel{
    override val onLoadingEnd = MutableLiveData<Boolean>()


    //전체 질문글 작성 제목 및 내용 있는지 체크
    var title = MutableStateFlow("")
    var content = MutableStateFlow("")

    //전체 질문글 작성 제목 및 내용의 내용
    var titleData = MutableLiveData<String>()
    var contentData = MutableLiveData<String>()

    //작성시 필요한 데이터
    var majorId = MutableLiveData<Int>()
    var answerId = MutableLiveData<Int>()

    //부적절 사용자 데이터들
    private var _statusCode = MutableLiveData<Int>()
    val statusCode: LiveData<Int>
        get() = _statusCode

    private var _message = MutableLiveData<String>()
    val message: LiveData<String>
        get() = _message

    //1:1, 전체 질문글, 정보글 작성
    var postDataWrite: MutableLiveData<ClassRoomPostWriteData> = MutableLiveData()

    //게시글 작성 서버
    private var _postWrite = MutableStateFlow(PostWriteData.DEFAULT)
    val postWrite: StateFlow<PostWriteData>
        get() = _postWrite

    //1:1, 전체 질문글, 정보글 수정
    private var _writeUpdate = MutableLiveData<WriteUpdateData>()
    val wrieUpdateData: LiveData<WriteUpdateData>
        get() = _writeUpdate

    //완료 버튼
    private var _completeButton = MutableStateFlow(false)
    val completeButton: StateFlow<Boolean>
        get() = _completeButton

    fun setCompleteButton() {
        viewModelScope.launch {
            combine(title, content) { title, content ->
                title.isNotEmpty() && content.isNotEmpty()
            }.collectLatest {
                _completeButton.value = it
            }
        }
    }


    //1:1, 질문, 정보글 등록
    fun postClassRoomWrite() {
        viewModelScope.launch {
            postRepository.postWrite(
                PostWriteParam(
                    type = "questionToPerson",
                    majorId = majorId.value.toString(),
                    answerId = answerId.value.toString(),
                    title = title.value,
                    content = content.value
                )
            ).catch {
                Timber.d("작성 실패")
            }.collectLatest {
                Timber.d("작성 성공")
                _postWrite.value = it
            }.also {
                onLoadingEnd.value = true
            }
        }
    }

    //1:1, 질문, 정보글 수정
    fun putWriteUpdate(postId: Int, writeUpdateItem: WriteUpdateItem) {
        viewModelScope.launch {
            runCatching { putWriteUpdateUseCase(postId, writeUpdateItem) }
                .onSuccess {
                    _writeUpdate.value = it
                    Timber.d("writeUpdate : 글 수정 완료")
                }
                .onFailure {
                    it.printStackTrace()
                    Timber.d("writeUpdate : 글 수정 실패")
                }
        }

    }


}