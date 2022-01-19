package com.nadosunbae_android.data.api.classroom

import com.nadosunbae_android.data.model.request.classroom.RequestClassRoomPostData
import com.nadosunbae_android.data.model.response.classroom.ResponseClassRoomMainData
import com.nadosunbae_android.data.model.response.classroom.ResponseClassRoomQuestionDetail
import com.nadosunbae_android.data.model.response.classroom.ResponseClassRoomWriteData
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
}
