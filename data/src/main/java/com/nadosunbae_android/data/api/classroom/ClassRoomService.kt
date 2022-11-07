package com.nadosunbae_android.data.api.classroom

import com.nadosunbae_android.data.model.request.classroom.RequestReportData
import com.nadosunbae_android.data.model.request.classroom.RequestWriteUpdateData
import com.nadosunbae_android.data.model.response.classroom.ResponseClassRoomMainData
import com.nadosunbae_android.data.model.response.classroom.ResponseDeleteComment
import com.nadosunbae_android.data.model.response.classroom.ResponseReportData
import com.nadosunbae_android.data.model.response.classroom.ResponseWriteUpdateData
import retrofit2.http.*

interface ClassRoomService {


    //과방 질문탭 메인
    @GET("classroom-post/{postTypeId}/major/{majorId}/list")
    suspend fun getClassRoomMain(
        @Path("postTypeId") postTypeId: Int,
        @Path("majorId") majorId: Int,
        @Query("sort") sort: String = "recent"
    ): ResponseClassRoomMainData


    //1:1 질문, 전체 질문, 정보글 원글 수정
    @PUT("classroom-post/{postId}")
    suspend fun putWriteUpdate(
        @Path("postId") postId: Int,
        @Body requestWriteUpdateData: RequestWriteUpdateData
    ): ResponseWriteUpdateData

    //1:1 질문, 전체 질문, 정보글에 있는 원글 삭제
    @DELETE("classroom-post/{postId}")
    suspend fun deletePost(
        @Path("postId") postId: Int
    ): ResponseDeleteComment

    //신고
    @POST("report")
    suspend fun postReport(
        @Body requestReportData: RequestReportData
    ): ResponseReportData
}
