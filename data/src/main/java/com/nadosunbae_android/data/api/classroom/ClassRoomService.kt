package com.nadosunbae_android.data.api.classroom

import com.nadosunbae_android.data.model.request.classroom.*
import com.nadosunbae_android.data.model.response.classroom.*
import retrofit2.http.*

interface ClassRoomService {


    //과방 질문탭 메인
    @GET("classroom-post/{postTypeId}/major/{majorId}/list")
    suspend fun getClassRoomMain(
        @Path("postTypeId") postTypeId : Int,
        @Path("majorId") majorId : Int,
        @Query("sort") sort : String = "recent"
    ) : ResponseClassRoomMainData

    //과방 1:1질문, 질문 상세 조회
    @GET("classroom-post/question/{postId}")
    suspend fun getClassRoomQuestionDetail(
        @Path("postId") postId : Int
    ) : ResponseClassRoomQuestionDetail


    // 1:1질문, 전체 질문, 정보글 등록

    @POST("classroom-post")
    suspend fun postClassRoomWrite(
        @Body requestClassRoomPostData: RequestClassRoomPostData
    ) : ResponseClassRoomWriteData


    // 가능한 선배들 모음
    @GET("user/mypage/list/major/{majorId}")
    suspend fun getClassRoomSenior(
        @Path("majorId") majorId: Int
    ) : ResponseClassRoomSeniorData

    // 1:1질문, 전체 질문, 정보글에 댓글 등록
    @POST("comment")
    suspend fun postQuestionCommentWrite(
        @Body requestQuestionCommentWriteData: RequestQuestionCommentWriteData
    ) : ResponseQuestionCommentWrite


    //선배 개인페이지
    @GET("user/mypage/{userId}")
    suspend fun getSeniorPersonal(
       @Path("userId") userId : Int
    ) : ResponseSeniorPersonalData

    //선배 개인페이지 1:1 질문 글 목록
    @GET("user/mypage/{userId}/classroom-post/list")
    suspend fun getSeniorQuestionList(
        @Path("userId") userId : Int,
        @Query("sort") sort : String
    ) : ResponseSeniorQuestionData


    //정보글 상세보기
    @GET("classroom-post/information/{postId}")
    suspend fun getInformationDetail(
        @Path("postId") postId : Int
    ) : ResponseInfoDetailData

    //1:1 질문, 전체 질문, 정보글에 있는 특정 댓글 수정
    @PUT("comment/{commentId}")
    suspend fun putCommentUpdate(
        @Path("commentId") commentId : Int,
        @Body requestCommentUpdateData : RequestCommentUpdateData
    ) : ResponseCommentUpdateData

    //1:1 질문, 전체 질문, 정보글 원글 수정
    @PUT("classroom-post/{postId}")
    suspend fun putWriteUpdate(
        @Path("postId") postId : Int,
        @Body requestWriteUpdateData : RequestWriteUpdateData
    ) : ResponseWriteUpdateData

    //1:1 질문, 전체 질문, 정보글에 있는 특정 댓글 삭제
    @DELETE("comment/{commentId}")
    suspend fun deleteComment(
        @Path("commentId") commentId : Int
    ) : ResponseDeleteComment

    //1:1 질문, 전체 질문, 정보글에 있는 원글 삭제
    @DELETE("classroom-post/{postId}")
    suspend fun deletePost(
        @Path("postId") postId : Int
    ) : ResponseDeleteComment

    //신고
    @POST("report")
    suspend fun postReport(
        @Body requestReportData : RequestReportData
    ) : ResponseReportData
}
