package com.nadosunbae_android.data.repository.classroom

import com.nadosunbae_android.data.model.response.classroom.ResponseClassRoomMainData
import com.nadosunbae_android.data.model.response.classroom.ResponseClassRoomQuestionDetail
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

}