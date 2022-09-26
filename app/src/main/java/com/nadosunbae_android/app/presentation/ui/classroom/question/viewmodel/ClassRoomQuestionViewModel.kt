package com.nadosunbae_android.app.presentation.ui.classroom.question.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nadosunbae_android.app.presentation.base.LoadableViewModel
import com.nadosunbae_android.domain.model.classroom.ClassRoomSeniorData
import com.nadosunbae_android.domain.model.user.SeniorListData
import com.nadosunbae_android.domain.repository.classroom.ClassRoomRepository
import com.nadosunbae_android.domain.repository.user.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ClassRoomQuestionViewModel @Inject constructor(
    private val userRepository: UserRepository
    ) : ViewModel(), LoadableViewModel {

    override val onLoadingEnd = MutableLiveData<Boolean>()

    private val _seniorList = MutableLiveData<ClassRoomSeniorData>()
    val seniorList: LiveData<ClassRoomSeniorData>
        get() = _seniorList

    fun getSeniorList(
        majorId: Int,
        exclude: String?
    ) {
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
}