package com.nadosunbae_android.app.presentation.ui.classroom.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nadosunbae_android.app.presentation.base.LoadableViewModel
import com.nadosunbae_android.app.util.DropDownSelectableViewModel
import com.nadosunbae_android.domain.model.classroom.ClassRoomData
import com.nadosunbae_android.domain.model.classroom.SeniorPersonalData
import com.nadosunbae_android.domain.model.main.SelectableData
import com.nadosunbae_android.domain.model.mypage.MyPageBlockUpdateData
import com.nadosunbae_android.domain.model.mypage.MyPageBlockUpdateItem
import com.nadosunbae_android.domain.usecase.classroom.GetQuestionSeniorListDataUseCase
import com.nadosunbae_android.domain.usecase.classroom.GetSeniorPersonalDataUseCase
import com.nadosunbae_android.domain.usecase.mypage.PostMyPageBlockUpdateUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

import timber.log.Timber

class SeniorPersonalViewModel(
    val getSeniorPersonalDataUseCase: GetSeniorPersonalDataUseCase,
    val getQuestionSeniorListDataUseCase : GetQuestionSeniorListDataUseCase,
    val postMyPageBlockUpdateUseCase : PostMyPageBlockUpdateUseCase
) : ViewModel(), DropDownSelectableViewModel, LoadableViewModel {

    override var dropDownSelected = MutableLiveData<SelectableData>()


    //선배 개인페이지
    private val _seniorPersonal = MutableStateFlow(
        SeniorPersonalData("","",false,"",0,"","",0,0)
    )
    val seniorPersonal : StateFlow<SeniorPersonalData>
        get() = _seniorPersonal

    //선배 1:1 질문
    private val _seniorQuestion = MutableStateFlow(
        listOf<ClassRoomData>()
    )
    val seniorQuestion : StateFlow<List<ClassRoomData>>
        get() = _seniorQuestion

    //선배 userId
    var seniorId = MutableLiveData<Int>()

    //선배 차단
    private var _blockData = MutableLiveData<MyPageBlockUpdateData>()
    val blockData : LiveData<MyPageBlockUpdateData>
        get() = _blockData

    //선배 개인페이지 정보 서버통신
    fun getSeniorPersonal(userId : Int){
        viewModelScope.launch {
            runCatching { getSeniorPersonalDataUseCase(userId) }
                .onSuccess {
                    it.collectLatest { its ->
                        _seniorPersonal.value = its
                    }
                    Timber.d("seniorPersonal : 선배 개인페이지 서버 통신 완료")
                }
                .onFailure {
                    Timber.d("seniorPersonal : 선배 개인페이지 서버 통신 실패")
                }.also {
                    onLoadingEnd.value = true
                }
        }
    }

    //선배 1:1 질문 리스트
    fun getSeniorQuestionList(userId : Int, sort : String){
        viewModelScope.launch {
            runCatching { getQuestionSeniorListDataUseCase(userId, sort) }
                .onSuccess {
                    it.collectLatest {its ->
                        _seniorQuestion.value = its
                    }
                    Timber.d("seniorQuestion : 선배 1:1질문 서버 통신 완료")
                }
                .onFailure {
                    it.printStackTrace()
                    Timber.d("seniorQuestion : 선배 1:1질문 서버 통신 실패")
                } .also {
                            onLoadingEnd.value = true
                        }
        }
    }

    //과방 차단 & 차단 해제
    fun postClassRoomBlockUpdate(myPageBlockUpdateItem: MyPageBlockUpdateItem) {
        viewModelScope.launch {
            kotlin.runCatching { postMyPageBlockUpdateUseCase(myPageBlockUpdateItem) }
                .onSuccess {
                    _blockData.value = it
                    Timber.d("classRoomBlockUpdate 서버 통신 완료")
                }
                .onFailure {
                    it.printStackTrace()
                    Timber.d("classRoomBlockUpdate 서버 통신 실패")
                }

        }
    }

    override val onLoadingEnd = MutableLiveData<Boolean>()
}


