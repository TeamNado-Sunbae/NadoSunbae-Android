package com.nadosunbae_android.presentation.ui.classroom.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nadosunbae_android.model.classroom.InfoDetailData
import com.nadosunbae_android.model.classroom.QuestionCommentWriteData
import com.nadosunbae_android.model.classroom.QuestionCommentWriteItem
import com.nadosunbae_android.usecase.classroom.GetInformationDetailUseCase
import com.nadosunbae_android.usecase.classroom.PostQuestionCommentWriteUseCase
import kotlinx.coroutines.launch

class InfoDetailViewModel(
    val getInformationDetailUseCase: GetInformationDetailUseCase,
    val postQuestionCommentWriteUseCase: PostQuestionCommentWriteUseCase
) : ViewModel() {


    //정보 상세 조회
    private val _infoDetailData = MutableLiveData<InfoDetailData>()
    val infoDetailData: LiveData<InfoDetailData>
        get() = _infoDetailData

    //정보 댓글 등록
    var registerInfoComment = MutableLiveData<QuestionCommentWriteData>()


    //정보 상세 조회 서버통신
    fun getInfoDetail(postId: Int) {
        viewModelScope.launch {
            runCatching { getInformationDetailUseCase(postId) }
                .onSuccess {
                    _infoDetailData.value = it
                    Log.d("infoDetail", "정보 상세보기 서버 통신 성공")
                }
                .onFailure {
                    it.printStackTrace()
                    Log.d("infoDetail", "정보 상세보기 서버 통신 실패")
                }
        }

    }

    //정보 상세 댓글 등록
    fun postInfoCommentWrite(
        questionCommentWriteItem: QuestionCommentWriteItem
    ) {
        viewModelScope.launch {
            runCatching { postQuestionCommentWriteUseCase(questionCommentWriteItem) }
                .onSuccess {
                    registerInfoComment.value = it
                    Log.d("infoComment", "댓글 통신 성공")
                }
                .onFailure {
                    it.printStackTrace()
                    Log.d("infoComment", "댓글 통신 실패 ")
                }
        }

    }
}

