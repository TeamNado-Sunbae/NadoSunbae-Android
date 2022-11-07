package com.nadosunbae_android.data.datasource.remote.classroom

import com.nadosunbae_android.data.api.classroom.ClassRoomService
import com.nadosunbae_android.data.model.request.classroom.RequestReportData
import com.nadosunbae_android.data.model.request.classroom.RequestWriteUpdateData
import com.nadosunbae_android.data.model.response.classroom.ResponseClassRoomMainData
import com.nadosunbae_android.data.model.response.classroom.ResponseDeleteComment
import com.nadosunbae_android.data.model.response.classroom.ResponseReportData
import com.nadosunbae_android.data.model.response.classroom.ResponseWriteUpdateData
import javax.inject.Inject

class ClassRoomDataSourceImpl @Inject constructor(private val service: ClassRoomService) :
    ClassRoomDataSource {
    override suspend fun getClassRoomMain(
        postTypeId: Int,
        majorId: Int,
        sort: String
    ): ResponseClassRoomMainData {
        return service.getClassRoomMain(postTypeId, majorId, sort)
    }

    //1:1 질문, 전체 질문, 정보글 원글 수정
    override suspend fun putWriteUpdate(
        postId: Int,
        requestWriteUpData: RequestWriteUpdateData
    ): ResponseWriteUpdateData {
        return service.putWriteUpdate(postId, requestWriteUpData)
    }


    override suspend fun deletePost(postId: Int): ResponseDeleteComment {
        return service.deletePost(postId)
    }

    override suspend fun postReport(requestReportData: RequestReportData): ResponseReportData {
        return service.postReport(requestReportData)
    }
}