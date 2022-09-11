package com.nadosunbae_android.data.datasource.remote.classroom

import com.nadosunbae_android.data.api.classroom.ClassRoomService
import com.nadosunbae_android.data.model.request.classroom.*
import com.nadosunbae_android.data.model.response.classroom.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ClassRoomDataSourceImpl @Inject constructor(private val service : ClassRoomService) : ClassRoomDataSource {
    override suspend fun getClassRoomMain(
        postTypeId: Int,
        majorId: Int,
        sort: String
    ): ResponseClassRoomMainData {
        return service.getClassRoomMain(postTypeId, majorId, sort)
    }

    override suspend fun getClassRoomQuestionDetail(postId: Int): ResponseClassRoomQuestionDetail {
        return service.getClassRoomQuestionDetail(postId)
    }

    override suspend fun postClassRoomWrite(requestClassRoomPostData: RequestClassRoomPostData): ResponseClassRoomWriteData {
        return service.postClassRoomWrite(requestClassRoomPostData)
    }

    override suspend fun getClassRoomSenior(majorId: Int): ResponseClassRoomSeniorData {
        return service.getClassRoomSenior(majorId)
    }

    override suspend fun postQuestionCommentWrite(requestQuestionCommentWriteData: RequestQuestionCommentWriteData): ResponseQuestionCommentWrite {
        return service.postQuestionCommentWrite(requestQuestionCommentWriteData)
    }

    override fun getSeniorPersonal(userId: Int): Flow<ResponseSeniorPersonalData> {
        return flow{
            emit(service.getSeniorPersonal(userId))
        }

    }

    override  fun getSeniorQuestionList(
        userId: Int,
        sort: String
    ): Flow<ResponseSeniorQuestionData> {
        return flow {
            emit(service.getSeniorQuestionList(userId, sort))
        }
    }


    override suspend fun getInformationDetail(postId: Int): ResponseInfoDetailData {
        return service.getInformationDetail(postId)
    }

    override suspend fun putCommentUpdate(
        commentId: Int,
        requestCommentUpdateData: RequestCommentUpdateData
    ): ResponseCommentUpdateData {
        return service.putCommentUpdate(commentId, requestCommentUpdateData)
    }

    //1:1 질문, 전체 질문, 정보글 원글 수정

    override suspend fun putWriteUpdate(
        postId: Int,
        requestWriteUpData: RequestWriteUpdateData
    ): ResponseWriteUpdateData {
        return service.putWriteUpdate(postId,requestWriteUpData)
    }

    override suspend fun deleteComment(commentId: Int): ResponseDeleteComment {
        return service.deleteComment(commentId)
    }

    override suspend fun deletePost(postId: Int): ResponseDeleteComment {
        return service.deletePost(postId)
    }

    override suspend fun postReport(requestReportData: RequestReportData): ResponseReportData {
        return service.postReport(requestReportData)
    }
}