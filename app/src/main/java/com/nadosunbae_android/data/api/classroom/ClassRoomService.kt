package com.nadosunbae_android.data.api.classroom

import com.nadosunbae_android.data.model.request.classroom.RequestClassRoomPostData
import com.nadosunbae_android.data.model.request.classroom.RequestQuestionCommentWriteData
import com.nadosunbae_android.data.model.response.classroom.*
import retrofit2.Call
import retrofit2.http.*

interface ClassRoomService {


    //과방 질문탭 메인
    @GET("classroom-post/{postTypeId}/major/{majorId}/list")
    fun getClassRoomMain(
        @Path("postTypeId") postTypeId : Int,
        @Path("majorId") majorId : Int,
        @Query("sort") sort : String = "recent"
    ) : Call<ResponseClassRoomMainData>

    //과방 1:1질문, 질문 상세 조회
    @GET("classroom-post/question/{postId}")
    fun getClassRoomQuestionDetail(
        @Path("postId") postId : Int
    ) : Call<ResponseClassRoomQuestionDetail>


    // 1:1질문, 전체 질문, 정보글 등록

    @POST("classroom-post")
    fun postClassRoomWrite(
        @Body requestClassRoomPostData: RequestClassRoomPostData
    ) : Call<ResponseClassRoomWriteData>


    // 가능한 선배들 모음
    @GET("user/mypage/list/major/{majorId}")
    fun getClassRoomSenior(
        @Path("majorId") majorId: Int
    ) : Call<ResponseClassRoomSeniorData>

    // 1:1질문, 전체 질문, 정보글에 댓글 등록
    @POST("comment")
    fun postQuestionCommentWrite(
        @Body requestQuestionCommentWriteData: RequestQuestionCommentWriteData
    ) : Call<ResponseQuestionCommentWrite>


    //선배 개인페이지
    @GET("user/mypage/{userId}")
    fun getSeniorPersonal(
       @Path("userId") userId : Int
    ) : Call<ResponseSeniorPersonalData>

    //선배 개인페이지 1:1 질문 글
}
