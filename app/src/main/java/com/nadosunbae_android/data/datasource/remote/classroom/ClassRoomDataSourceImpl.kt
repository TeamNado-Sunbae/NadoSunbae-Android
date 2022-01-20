package com.nadosunbae_android.data.datasource.remote.classroom

import com.nadosunbae_android.data.api.ApiService
import com.nadosunbae_android.data.model.request.classroom.RequestClassRoomPostData
import com.nadosunbae_android.data.model.request.classroom.RequestQuestionCommentWriteData
import com.nadosunbae_android.data.model.response.classroom.*
import com.nadosunbae_android.util.enqueueUtil
import retrofit2.Response

class ClassRoomDataSourceImpl() : ClassRoomDataSource {


    override fun getClassRoomMain(
        postTypeId: Int,
        majorId: Int,
        sort: String,
        onResponse: (Response<ResponseClassRoomMainData>) -> Unit,
        onFailure : (Throwable) -> Unit)
     {
        return ApiService.classRoomService.getClassRoomMain(postTypeId, majorId, sort).enqueueUtil(
            onResponse, onFailure
        )
    }

    override fun getClassRoomQuestionDetail(
        postId: Int,
        onResponse: (Response<ResponseClassRoomQuestionDetail>) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        return ApiService.classRoomService.getClassRoomQuestionDetail(postId).enqueueUtil(
            onResponse, onFailure
        )
    }

    //1:1질문, 질문글, 정보글 등록
    override fun postClassRoomWrite(
        requestClassRoomPostData: RequestClassRoomPostData,
        onResponse: (Response<ResponseClassRoomWriteData>) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        return ApiService.classRoomService.postClassRoomWrite(requestClassRoomPostData).enqueueUtil(
            onResponse, onFailure
        )
    }

    override fun getClassRoomSenior(
        majorId: Int,
        onResponse: (Response<ResponseClassRoomSeniorData>) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        return ApiService.classRoomService.getClassRoomSenior(majorId).enqueueUtil(
            onResponse, onFailure
        )
    }

    //댓글 작성
    override fun postQuestionCommentWrite(
        requestQuestionCommentWriteData: RequestQuestionCommentWriteData,
        onResponse: (Response<ResponseQuestionCommentWrite>) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        return ApiService.classRoomService.postQuestionCommentWrite(requestQuestionCommentWriteData).enqueueUtil(
            onResponse, onFailure
        )
    }

    //선배 개인페이지
    override fun getSeniorPersonal(
        userId: Int,
        onResponse: (Response<ResponseSeniorPersonalData>) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        return ApiService.classRoomService.getSeniorPersonal(userId).enqueueUtil(
            onResponse, onFailure
        )
    }

    //선배 1:1 질문글 리스트
    override fun getSeniorQuestionList(
        userId: Int,
        sort: String?,
        onResponse: (Response<ResponseSeniorQuestionData>) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        return ApiService.classRoomService.getSeniorQuestionList(userId, sort).enqueueUtil(
            onResponse, onFailure
        )
    }
}