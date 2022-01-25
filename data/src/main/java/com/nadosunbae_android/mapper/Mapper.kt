package com.nadosunbae_android.mapper

import com.nadosunbae_android.model.response.classroom.ResponseClassRoomMainData
import com.nadosunbae_android.model.response.classroom.ResponseSeniorQuestionData
import com.nadosunbae_android.model.response.mypage.ResponseMypageQuestionData
import com.nadosunbae_android.model.ui.classroom.ClassRoomData

object Mapper {
    fun mapperToQuestionMain(responseClassRoomMainData : ResponseClassRoomMainData) : List<ClassRoomData>{
        return responseClassRoomMainData.data.map {
            ClassRoomData(
                postId = it.postId,
                title = it.title,
                content = it.content,
                createdAt = it.createdAt,
                writer = ClassRoomData.Writer(
                    nickname = it.writer.nickname,
                    profileImageId = it.writer.profileImageId,
                    writerId = it.writer.writerId
                ),
                likeCount = it.likeCount,
                commentCount = it.commentCount
            )
        }
    }

    fun mapperToSeniorQuestion(responseSeniorQuestionData: ResponseSeniorQuestionData) : List<ClassRoomData>{
        return responseSeniorQuestionData.data.classroomPostList.map {
            ClassRoomData(
                postId = it.postId,
                title = it.title,
                content = it.content,
                createdAt = it.createdAt,
                writer = ClassRoomData.Writer(
                    nickname = it.writer.nickname,
                    profileImageId = it.writer.profileImageId,
                    writerId = it.writer.writerId
                ),
                likeCount = it.likeCount,
                commentCount = it.commentCount
            )
        }
    }

    fun mapperToMyPageQuestion(responseMypageQuestionData: ResponseMypageQuestionData) : List<ClassRoomData>{
        return responseMypageQuestionData.data.classroomPostList.map {
            ClassRoomData(
                postId = it.postId,
                title = it.title,
                content = it.content,
                createdAt = it.createdAt,
                writer = ClassRoomData.Writer(
                    nickname = it.writer.nickname,
                    profileImageId = it.writer.profileImageId,
                    writerId = it.writer.writerId
                ),
                likeCount = it.likeCount,
                commentCount = it.commentCount
            )

        }

    }

}