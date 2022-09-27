package com.nadosunbae_android.app.presentation.ui.classroom.question.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nadosunbae_android.app.presentation.base.LoadableViewModel
import com.nadosunbae_android.domain.model.classroom.ClassRoomSeniorData
import com.nadosunbae_android.domain.model.post.PostData
import com.nadosunbae_android.domain.model.user.SeniorListData
import com.nadosunbae_android.domain.repository.classroom.ClassRoomRepository
import com.nadosunbae_android.domain.repository.post.PostRepository
import com.nadosunbae_android.domain.repository.user.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ClassRoomQuestionViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val postRepository: PostRepository
    ) : ViewModel(), LoadableViewModel {

    override val onLoadingEnd = MutableLiveData<Boolean>()

    private val _seniorList = MutableLiveData<ClassRoomSeniorData>()
    val seniorList: LiveData<ClassRoomSeniorData>
        get() = _seniorList

    private val _questionList = MutableLiveData<List<PostData>>()
    val questionList: LiveData<List<PostData>>
        get() = _questionList

    fun getSeniorList(
        majorId: Int,
        exclude: String?
    ) {
        onLoadingEnd.value = false
        viewModelScope.launch {
            kotlin.runCatching {
                userRepository.getSeniorList(majorId, exclude)
            }.onSuccess {
                _seniorList.value = it
                Timber.d("CLASSROOM: success getSeniorList = $it")
            }.onFailure {
                Timber.d("CLASSROOM: fail getSeniorList")
            }.also {
                onLoadingEnd.value = true
            }
        }
    }

    fun getQuestionList(
        univId: Int,
        majorId: Int
    ) = viewModelScope.launch {
        postRepository.getPost(
            universityId = univId.toString(),
            majorId = majorId.toString(),
            sort = "recent",
            filter = "questionToPerson",
            search = null
        ).onStart {
            onLoadingEnd.value = false
        }.catch {
            Timber.d("최근 1:1 질문 불러오기 실패")
        }.collectLatest {
            _questionList.value = it
            Timber.d("최근 1:1 질문 불러오기 완료")
        }.also {
            onLoadingEnd.value = true
        }
    }
}