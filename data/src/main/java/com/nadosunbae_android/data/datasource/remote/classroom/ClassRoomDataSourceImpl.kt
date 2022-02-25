package com.nadosunbae_android.data.datasource.remote.classroom

import com.nadosunbae_android.data.api.classroom.ClassRoomService
import com.nadosunbae_android.data.model.request.classroom.RequestClassRoomPostData
import com.nadosunbae_android.data.model.request.classroom.RequestCommentUpdateData
import com.nadosunbae_android.data.model.request.classroom.RequestQuestionCommentWriteData
import com.nadosunbae_android.data.model.request.classroom.RequestWriteUpdateData
import com.nadosunbae_android.data.model.response.classroom.*

class ClassRoomDataSourceImpl(private val service : ClassRoomService) : ClassRoomDataSource {
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

    override suspend fun getSeniorPersonal(userId: Int): ResponseSeniorPersonalData {
        return service.getSeniorPersonal(userId)
    }

    override suspend fun getSeniorQuestionList(
        userId: Int,
        sort: String
    ): ResponseSeniorQuestionData {
        return service.getSeniorQuestionList(userId, sort)
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
}