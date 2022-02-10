package com.nadosunbae_android.datasource.remote.classroom

import com.nadosunbae_android.model.request.classroom.RequestClassRoomPostData
import com.nadosunbae_android.model.request.classroom.RequestQuestionCommentWriteData
import com.nadosunbae_android.model.response.classroom.*
import retrofit2.Response

interface ClassRoomDataSource {
    //메인 정보 조회
    suspend fun getClassRoomMain(postTypeId: Int, majorId: Int, sort: String = "recent") : ResponseClassRoomMainData

    // 상세보기
    suspend fun getClassRoomQuestionDetail(postId : Int) : ResponseClassRoomQuestionDetail



    //글 작성
    suspend fun postClassRoomWrite(requestClassRoomPostData: RequestClassRoomPostData) : ResponseClassRoomWriteData

    // 구성원 전체보기
    suspend fun getClassRoomSenior(majorId: Int) : ResponseClassRoomSeniorData

    //1:1질문, 전체 질문, 정보 댓글작성
    suspend fun postQuestionCommentWrite(requestQuestionCommentWriteData: RequestQuestionCommentWriteData) : ResponseQuestionCommentWrite

    //선배 개인페이지
    suspend fun getSeniorPersonal(userId : Int) : ResponseSeniorPersonalData

    //선배 1:1 질문 조회
    suspend fun getSeniorQuestionList(userId : Int, sort : String?) : ResponseSeniorQuestionData

    //정보 상세 조회
    suspend fun getInformationDetail(postId: Int) : ResponseInfoDetailData


}