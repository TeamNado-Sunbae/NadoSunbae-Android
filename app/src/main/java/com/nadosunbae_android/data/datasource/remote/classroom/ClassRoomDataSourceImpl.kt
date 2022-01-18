package com.nadosunbae_android.data.datasource.remote.classroom

import com.nadosunbae_android.data.api.ApiService
import com.nadosunbae_android.data.model.request.classroom.RequestClassRoomPostData
import com.nadosunbae_android.data.model.response.classroom.ResponseClassRoomMainData
import com.nadosunbae_android.data.model.response.classroom.ResponseClassRoomQuestionDetail
import com.nadosunbae_android.data.model.response.classroom.ResponseClassRoomWriteData
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
}