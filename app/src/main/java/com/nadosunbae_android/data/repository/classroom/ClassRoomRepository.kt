package com.nadosunbae_android.data.repository.classroom

import android.view.inspector.IntFlagMapping
import com.nadosunbae_android.data.model.request.classroom.RequestClassRoomPostData
import com.nadosunbae_android.data.model.request.classroom.RequestQuestionCommentWriteData
import com.nadosunbae_android.data.model.response.classroom.*
import retrofit2.Response

interface ClassRoomRepository {
    fun getClassRoomMain(
        postTypeId: Int, majorId: Int, sort: String = "recent",
        onResponse: (Response<ResponseClassRoomMainData>) -> Unit,
        onFailure : (Throwable) -> Unit)


    fun getClassRoomQuestionDetail(
        postId : Int,
        onResponse: (Response<ResponseClassRoomQuestionDetail>) -> Unit,
        onFailure : (Throwable) -> Unit
    )

    // 1:1, 질문, 정보글 등록
    fun postClassRoomWrite(
        requestClassRoomPostData: RequestClassRoomPostData,
        onResponse: (Response<ResponseClassRoomWriteData>) -> Unit,
        onFailure : (Throwable) -> Unit
    )

    //구성원 전체보기
    fun getClassRoomSenior(
        majorId: Int,
        onResponse : (Response<ResponseClassRoomSeniorData>) -> Unit,
        onFailure: (Throwable) -> Unit
    )

    //댓글 등록
    fun postQuestionCommentWrite(
        requestQuestionCommentWriteData: RequestQuestionCommentWriteData,
        onResponse : (Response<ResponseQuestionCommentWrite>) -> Unit,
        onFailure: (Throwable) -> Unit
    )

    //선배 개인페이지
    fun getSeniorPersonal(
        userId : Int,
        onResponse : (Response<ResponseSeniorPersonalData>) -> Unit,
        onFailure: (Throwable) -> Unit
    )
}