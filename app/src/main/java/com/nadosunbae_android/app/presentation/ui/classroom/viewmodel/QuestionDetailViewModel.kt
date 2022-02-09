package com.nadosunbae_android.app.presentation.ui.classroom.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nadosunbae_android.data.model.classroom.QuestionCommentWriteData
import com.nadosunbae_android.data.model.classroom.QuestionCommentWriteItem
import com.nadosunbae_android.data.model.classroom.QuestionDetailData
import com.nadosunbae_android.domain.usecase.classroom.GetQuestionDetailDataUseCase
import com.nadosunbae_android.domain.usecase.classroom.PostQuestionCommentWriteUseCase
import kotlinx.coroutines.launch

class QuestionDetailViewModel(
    val getQuestionDetailDataUseCase : GetQuestionDetailDataUseCase,
    val postQuestionCommentWriteUseCase: PostQuestionCommentWriteUseCase
) : ViewModel() {


    //전체 질문 상세보기 데이터
    private var _questionDetailData = MutableLiveData<QuestionDetailData>()
    val questionDetailData : LiveData<QuestionDetailData>
        get() = _questionDetailData


    //댓글 등록
    var registerComment = MutableLiveData<QuestionCommentWriteData>()



    //전체 질문 상세보기 서버 통신
    fun getClassRoomQuestionDetail(postId : Int){
        viewModelScope.launch {
            runCatching { getQuestionDetailDataUseCase(postId) }
                .onSuccess {
                    _questionDetailData.value = it
                    Log.d("classRoomDetail", "메인 서버 통신 성공")
                }
                .onFailure {
                    it.printStackTrace()
                    Log.d("classRoomMain", "메인 서버 통신 실패")
                }
        }
    }

    //댓글 등록 서버 통신
    fun postQuestionCommentWrite(questionCommentWriteItem: QuestionCommentWriteItem){
        viewModelScope.launch {
            runCatching {postQuestionCommentWriteUseCase(questionCommentWriteItem)  }
                .onSuccess {
                    registerComment.value = it
                    Log.d("questionComment", "댓글 통신 성공")
                }
                .onFailure {
                    it.printStackTrace()
                    Log.d("questionComment", "댓글 통신 실패 ")
                }
        }
    }
}