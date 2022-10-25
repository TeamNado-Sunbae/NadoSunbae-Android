package com.nadosunbae_android.data.datasource.remote.classroom

import com.nadosunbae_android.data.model.request.classroom.*
import com.nadosunbae_android.data.model.response.classroom.*
import kotlinx.coroutines.flow.Flow

interface ClassRoomDataSource {
    //메인 정보 조회
    suspend fun getClassRoomMain(postTypeId: Int, majorId: Int, sort: String) : ResponseClassRoomMainData


    //1:1 질문, 전체 질문, 정보글 원글 수정
    suspend fun putWriteUpdate(postId: Int, requestWriteUpData : RequestWriteUpdateData) : ResponseWriteUpdateData


    //1:1 질문, 전체 질문, 정보글에 있는 원글 삭제
    suspend fun deletePost(postId : Int) : ResponseDeleteComment

    //신고하기
    suspend fun postReport(requestReportData: RequestReportData) : ResponseReportData
}