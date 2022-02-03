package com.nadosunbae_android.datasource.remote.classroom

import com.nadosunbae_android.api.classroom.ClassRoomService
import com.nadosunbae_android.model.request.classroom.RequestClassRoomPostData
import com.nadosunbae_android.model.request.classroom.RequestQuestionCommentWriteData
import com.nadosunbae_android.model.response.classroom.*

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
        sort: String?
    ): ResponseSeniorQuestionData {
        return service.getSeniorQuestionList(userId, sort)
    }

    override suspend fun getInformationDetail(postId: Int): ResponseInfoDetailData {
        return service.getInformationDetail(postId)
    }
}