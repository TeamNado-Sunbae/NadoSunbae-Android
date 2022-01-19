package com.nadosunbae_android.data.repository.classroom

import android.view.inspector.IntFlagMapping
import com.nadosunbae_android.data.model.request.classroom.RequestClassRoomPostData
import com.nadosunbae_android.data.model.response.classroom.ResponseClassRoomMainData
import com.nadosunbae_android.data.model.response.classroom.ResponseClassRoomQuestionDetail
import com.nadosunbae_android.data.model.response.classroom.ResponseClassRoomSeniorData
import com.nadosunbae_android.data.model.response.classroom.ResponseClassRoomWriteData
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
}