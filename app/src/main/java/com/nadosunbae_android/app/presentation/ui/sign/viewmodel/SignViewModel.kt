package com.nadosunbae_android.app.presentation.ui.sign.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nadosunbae_android.app.presentation.base.LoadableViewModel
import com.nadosunbae_android.domain.model.main.SelectableData
import com.nadosunbae_android.domain.model.major.MajorListData
import com.nadosunbae_android.domain.repository.major.MajorRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SignViewModel @Inject constructor(
    private val majorRepository: MajorRepository
) : ViewModel() {


    var text = MutableLiveData<String>()
    var firstMajor = MutableLiveData<String>()
    var firstMajorPeriod = MutableLiveData<String>()
    var secondMajor = MutableLiveData<String>()
    var secondMajorPeriod = MutableLiveData<String>()
    var deviceToken = MutableLiveData<String>()


    //학과 변경 리스트
    private var _firstMajorList = MutableLiveData<List<MajorListData>>()
    val firstMajorList: LiveData<List<MajorListData>>
        get() = _firstMajorList

    //학과 변경 리스트
    private var _secondMajorList = MutableLiveData<List<MajorListData>>()
    val secondMajorList: LiveData<List<MajorListData>>
        get() = _secondMajorList

    //학과 선택 내용
    private var _firstFilter = MutableStateFlow(SelectableData.DEFAULT)
    val firstFilter: StateFlow<SelectableData>
        get() = _firstFilter

    //학과 선택 내용
    private var _secondFilter = MutableStateFlow(SelectableData.DEFAULT)
    val secondFilter: StateFlow<SelectableData>
        get() = _secondFilter

    fun setFirstFilter(filter: SelectableData) {
        _firstFilter.value = filter
    }

    fun setSecondFilter(filter: SelectableData) {
        _secondFilter.value = filter
    }

    //학과 리스트 가져오기
    fun getMajorList(
        universityId: Int, filter: String, exclude: String?,
        userId: Int
    ) {
        viewModelScope.launch {
            majorRepository.deleteMajorList()
            majorRepository.getMajorList(universityId, filter, exclude, userId)
                .onStart {
                    //onLoadingEnd.value = false
                }
                .catch {
                    Timber.d("학과 리스트 가져오기 실패 ${it.printStackTrace()}")
                }
                .collectLatest {
                    if (filter == "firstMajor") {
                        _firstMajorList.value = it

                    } else {
                        _secondMajorList.value = it
                    }

                }
        }
    }
}
