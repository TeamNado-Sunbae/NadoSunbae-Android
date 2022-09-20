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

    private val _seniorList = MutableLiveData<SeniorListData>()
    val seniorList: LiveData<SeniorListData>
        get() = _seniorList

    fun getSeniorList(
        majorId: Int,
        exclude: String
    ) = viewModelScope.launch {
        userRepository.getSeniorList(majorId, exclude)
            .onStart {
                onLoadingEnd.value = false
            }
            .catch {
                Timber.d("CLASSROOM: start getSeniorList")
            }
            .collectLatest {
                Timber.d("CLASSROOM: end getSeniorList")
                _seniorList.value = it
            }
            .also {
                onLoadingEnd.value = true
            }
    }
}