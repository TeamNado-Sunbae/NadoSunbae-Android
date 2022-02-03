package com.nadosunbae_android.repository.classroom

import com.nadosunbae_android.model.classroom.*

interface ClassRoomRepository {
    //질문 메인
    suspend fun getClassRoomMain(postTypeId: Int, majorId: Int, sort: String = "recent") : List<ClassRoomData>

    //질문 상세
    suspend fun getClassRoomQuestionDetail(postId : Int) : QuestionDetailData

    // 1:1, 질문, 정보글 등록
    suspend fun postClassRoomWrite(classRoomPostWriteItem: ClassRoomPostWriteItem) : ClassRoomPostWriteData

    //구성원 전체보기
    suspend fun getClassRoomSenior(majorId: Int) : ClassRoomSeniorData

    //댓글 등록
    suspend fun postQuestionCommentWrite(questionCommentWriteItem: QuestionCommentWriteItem) : QuestionCommentWriteData

    //선배 개인페이지
    suspend fun getSeniorPersonal(userId : Int) : QuestionCommentWriteData

    //선배 1:1 질문글 리스트
    suspend fun getSeniorQuestionList(userId : Int, sort : String) : List<ClassRoomData>

    //정보 상세 조회
    suspend fun getInformationDetail(postId: Int) : InfoDetailData

}