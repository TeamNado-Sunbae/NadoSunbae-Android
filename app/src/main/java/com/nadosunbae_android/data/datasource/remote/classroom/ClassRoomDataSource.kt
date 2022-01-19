package com.nadosunbae_android.data.datasource.remote.classroom

import com.nadosunbae_android.data.model.request.classroom.RequestClassRoomPostData
import com.nadosunbae_android.data.model.request.classroom.RequestQuestionCommentWriteData
import com.nadosunbae_android.data.model.response.classroom.*
import retrofit2.Response

interface ClassRoomDataSource {
    //메인 정보 조회
    fun getClassRoomMain(
        postTypeId: Int, majorId: Int, sort: String = "recent",
        onResponse: (Response<ResponseClassRoomMainData>) -> Unit,
        onFailure : (Throwable) -> Unit)

    // 상세보기
    fun getClassRoomQuestionDetail(
        postId : Int,
        onResponse: (Response<ResponseClassRoomQuestionDetail>) -> Unit,
        onFailure : (Throwable) -> Unit)


    //글 작성
    fun postClassRoomWrite(
        requestClassRoomPostData: RequestClassRoomPostData,
        onResponse: (Response<ResponseClassRoomWriteData>) -> Unit,
        onFailure : (Throwable) -> Unit)

    // 구성원 전체보기
    fun getClassRoomSenior(
        majorId: Int,
        onResponse: (Response<ResponseClassRoomSeniorData>) -> Unit,
        onFailure: (Throwable) -> Unit
    )

    //1:1질문, 전체 질문, 정보 댓글작성
    fun postQuestionCommentWrite(
        requestQuestionCommentWriteData: RequestQuestionCommentWriteData,
        onResponse: (Response<ResponseQuestionCommentWrite>) -> Unit,
        onFailure: (Throwable) -> Unit
    )

}