package com.nadosunbae_android.domain.repository.classroom

import com.nadosunbae_android.domain.model.classroom.*
import kotlinx.coroutines.flow.Flow

interface ClassRoomRepository {
    //질문 메인
    suspend fun getClassRoomMain(postTypeId: Int, majorId: Int, sort: String = "recent") : List<ClassRoomData>









    //1:1 질문, 전체 질문, 정보글 원글 수정
    suspend fun putWriteUpdate(postId: Int, writeUpdateItem: WriteUpdateItem) : WriteUpdateData


    //1:1 질문, 전체 질문, 정보글에 있는 원글 삭제
    suspend fun deletePost(postId : Int) : DeleteCommentData

    //신고
    suspend fun postReport(reportItem: ReportItem) : ReportData
}