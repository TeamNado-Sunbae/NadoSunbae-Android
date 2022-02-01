package com.nadosunbae_android.presentation.ui.classroom.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nadosunbae_android.model.request.classroom.RequestQuestionCommentWriteData
import com.nadosunbae_android.model.response.classroom.ResponseInfoDetailData
import com.nadosunbae_android.model.response.classroom.ResponseQuestionCommentWrite
import com.nadosunbae_android.repositoryimpl.classroom.ClassRoomRepository

class InfoDetailViewModel : ViewModel() {
    private val classRoomRepository: ClassRoomRepository = ClassRoomRepositoryImpl()


    //정보 상세 조회
    private val _infoDetailData = MutableLiveData<ResponseInfoDetailData>()
    val infoDetailData: LiveData<ResponseInfoDetailData>
        get() = _infoDetailData

    //정보 댓글 등록
    var registerInfoComment = MutableLiveData<ResponseQuestionCommentWrite>()


    //정보 상세 조회 서버통신
    fun getInfoDetail(postId: Int) {
        classRoomRepository.getInformationDetail(postId,
            onResponse = {
                if (it.isSuccessful) {
                    _infoDetailData.value = it.body()
                    Log.d("infoDetail", "정보 상세보기 서버 통신 성공")
                }
            },
            onFailure = {
                it.printStackTrace()
                Log.d("infoDetail", "정보 상세보기 서버 통신 실패")
            }
        )
    }

    //정보 상세 댓글 등록
    fun postInfoCommentWrite(
        requestQuestionCommentWriteData: RequestQuestionCommentWriteData
    ) {
        classRoomRepository.postQuestionCommentWrite(requestQuestionCommentWriteData,
            onResponse = {
                if (it.isSuccessful) {
                    registerInfoComment.value = it.body()
                    Log.d("infoComment", "댓글 통신 성공")
                }
            },
            onFailure = {
                it.printStackTrace()
                Log.d("infoComment", "댓글 통신 실패 ")
            }
        )
    }
}

