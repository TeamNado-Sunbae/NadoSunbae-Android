package com.nadosunbae_android.presentation.ui.classroom.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nadosunbae_android.model.request.classroom.RequestQuestionCommentWriteData
import com.nadosunbae_android.model.response.classroom.ResponseClassRoomQuestionDetail
import com.nadosunbae_android.model.response.classroom.ResponseQuestionCommentWrite
import com.nadosunbae_android.repositoryimpl.classroom.ClassRoomRepository

class QuestionDetailViewModel : ViewModel() {
    val classRoomRepository: ClassRoomRepository = ClassRoomRepositoryImpl()

    //전체 질문 상세보기 데이터
    private var _questionDetailData = MutableLiveData<ResponseClassRoomQuestionDetail>()
    val questionDetailData : LiveData<ResponseClassRoomQuestionDetail>
        get() = _questionDetailData


    //댓글 등록
    var registerComment = MutableLiveData<ResponseQuestionCommentWrite>()



    //전체 질문 상세보기 서버 통신
    fun getClassRoomQuestionDetail(postId : Int){
        classRoomRepository.getClassRoomQuestionDetail(postId,
            onResponse = {
                if(it.isSuccessful){
                    _questionDetailData.value = it.body()
                    Log.d("classRoomDetail", "메인 서버 통신 성공")
                }},
            onFailure = {
                it.printStackTrace()
                Log.d("classRoomMain", "메인 서버 통신 실패")
            }
        )
    }

    //댓글 등록 서버 통신
    fun postQuestionCommentWrite(requestQuestionCommentWriteData: RequestQuestionCommentWriteData){
        classRoomRepository.postQuestionCommentWrite(requestQuestionCommentWriteData,
            onResponse = {
                if(it.isSuccessful){
                    registerComment.value = it.body()
                    Log.d("questionComment", "댓글 통신 성공")
                }},
            onFailure = {
                it.printStackTrace()
                Log.d("questionComment", "댓글 통신 실패 ")
            }
        )
    }
}