package com.nadosunbae_android.data.repository.classroom

import com.nadosunbae_android.data.datasource.remote.classroom.ClassRoomDataSource
import com.nadosunbae_android.data.datasource.remote.classroom.ClassRoomDataSourceImpl
import com.nadosunbae_android.data.model.request.classroom.RequestClassRoomPostData
import com.nadosunbae_android.data.model.response.classroom.ResponseClassRoomMainData
import com.nadosunbae_android.data.model.response.classroom.ResponseClassRoomQuestionDetail
import com.nadosunbae_android.data.model.response.classroom.ResponseClassRoomSeniorData
import com.nadosunbae_android.data.model.response.classroom.ResponseClassRoomWriteData
import retrofit2.Response

class ClassRoomRepositoryImpl : ClassRoomRepository {
    val classRoomDataSource : ClassRoomDataSource = ClassRoomDataSourceImpl()

    override fun getClassRoomMain(
        postTypeId: Int,
        majorId: Int,
        sort: String,
        onResponse: (Response<ResponseClassRoomMainData>) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        return classRoomDataSource.getClassRoomMain(postTypeId, majorId, sort, onResponse, onFailure)
    }

    override fun getClassRoomQuestionDetail(
        postId: Int,
        onResponse: (Response<ResponseClassRoomQuestionDetail>) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        return classRoomDataSource.getClassRoomQuestionDetail(postId,onResponse, onFailure)
    }

    // 작성하기
    override fun postClassRoomWrite(
        requestClassRoomPostData: RequestClassRoomPostData,
        onResponse: (Response<ResponseClassRoomWriteData>) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        return classRoomDataSource.postClassRoomWrite(requestClassRoomPostData, onResponse, onFailure)
    }


    //구성원 전체 보기
    override fun getClassRoomSenior(
        majorId: Int,
        onResponse: (Response<ResponseClassRoomSeniorData>) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        return classRoomDataSource.getClassRoomSenior(majorId, onResponse, onFailure)
    }
}