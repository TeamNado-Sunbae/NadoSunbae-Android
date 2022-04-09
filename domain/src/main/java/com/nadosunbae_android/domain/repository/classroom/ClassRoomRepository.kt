package com.nadosunbae_android.domain.repository.classroom

import com.nadosunbae_android.domain.model.classroom.*
import kotlinx.coroutines.flow.Flow

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
    fun getSeniorPersonal(userId : Int) : Flow<SeniorPersonalData>

    //선배 1:1 질문글 리스트
    fun getSeniorQuestionList(userId : Int, sort : String) : Flow<List<ClassRoomData>>

    //정보 상세 조회
    suspend fun getInformationDetail(postId: Int) : InfoDetailData

    //1:1 질문, 전체 질문, 정보글에 있는 특정 댓글 수정
    suspend fun putCommentUpdate(commentId : Int, commentUpdateItem: CommentUpdateItem) : CommentUpdateData

    //1:1 질문, 전체 질문, 정보글 원글 수정
    suspend fun putWriteUpdate(postId: Int, writeUpdateItem: WriteUpdateItem) : WriteUpdateData

    // 1:1질문, 전체 질문 댓글 삭제
    suspend fun deleteComment(commentId : Int) : DeleteCommentData

    //1:1 질문, 전체 질문, 정보글에 있는 원글 삭제
    suspend fun deletePost(postId : Int) : DeleteCommentData

    //신고
    suspend fun postReport(reportItem: ReportItem) : ReportData
}