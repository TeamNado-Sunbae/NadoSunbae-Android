package com.nadosunbae_android.mapper.classroom

import com.nadosunbae_android.model.classroom.*
import com.nadosunbae_android.model.response.mypage.ResponseMypageQuestionData
import com.nadosunbae_android.model.request.classroom.RequestClassRoomData
import com.nadosunbae_android.model.request.classroom.RequestClassRoomPostData
import com.nadosunbae_android.model.request.classroom.RequestQuestionCommentWriteData
import com.nadosunbae_android.model.response.classroom.*

object ClassRoomMapper {
    //QuestionFragment
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
    //구성원 전체보기 선배 데이터
    fun mapperToSeniorData(responseClassRoomSeniorData : ResponseClassRoomSeniorData) : ClassRoomSeniorData{
        return ClassRoomSeniorData(
            offQuestionUserList = responseClassRoomSeniorData.data.offQuestionUserList.map {
            ClassRoomSeniorData.OffQuestionUser(
                it.profileImageId,
                it.isFirstMajor,
                it.isOnQuestion,
                it.majorStart,
                it.nickname,
                it.userId
            )
        },
            onQuestionUserList = responseClassRoomSeniorData.data.onQuestionUserList.map {
                ClassRoomSeniorData.OnQuestionUser(
                    it.profileImageId,
                    it.isFirstMajor,
                    it.isOnQuestion,
                    it.majorStart,
                    it.nickname,
                    it.userId
                )
            }
        )

    }


    // 선배 상세조회
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

    //마이페이지
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

    // 질문 상세 데이터
    fun mapperToQuestionDetailData(responseClassRoomQuestionDetail : ResponseClassRoomQuestionDetail) : QuestionDetailData{
        return QuestionDetailData(
            answererId = responseClassRoomQuestionDetail.data.answererId,
           isLiked = responseClassRoomQuestionDetail.data.like.isLiked,
            likeCount = responseClassRoomQuestionDetail.data.like.likeCount,
            messageList = responseClassRoomQuestionDetail.data.messageList.map {
                QuestionDetailData.Message(
                    it.content,
                    it.createdAt,
                    it.isDeleted,
                    it.messageId,
                    it.title,
                    it.writer.firstMajorName,
                    it.writer.firstMajorStart,
                    it.writer.isQuestioner,
                    it.writer.nickname,
                    it.writer.profileImageId,
                    it.writer.secondMajorName,
                    it.writer.secondMajorStart,
                    it.writer.writerId)
            },
            questionerId = responseClassRoomQuestionDetail.data.questionerId
        )
    }

    // 1:1질문, 전체질문, 정보글 등록 받는 데이터
    fun mapperToClassRoomPostWriteData(responseClassRoomWriteData: ResponseClassRoomWriteData) : ClassRoomPostWriteData{
        return ClassRoomPostWriteData(
            content = responseClassRoomWriteData.data.post.content,
            createdAt = responseClassRoomWriteData.data.post.createdAt,
            postId = responseClassRoomWriteData.data.post.postId,
            title = responseClassRoomWriteData.data.post.title,
            firstMajorName = responseClassRoomWriteData.data.writer.firstMajorName,
            firstMajorStart = responseClassRoomWriteData.data.writer.firstMajorStart,
            nickname = responseClassRoomWriteData.data.writer.nickname,
            profileImageId = responseClassRoomWriteData.data.writer.profileImageId,
            secondMajorName = responseClassRoomWriteData.data.writer.secondMajorName,
            secondMajorStart = responseClassRoomWriteData.data.writer.secondMajorStart,
            writerId = responseClassRoomWriteData.data.writer.writerId
        )
    }

    // 1:1질문, 전체질문, 정보글 등록하는 데이터
    fun mapperToClassRoomPostWriteItem(classRoomPostWriteItem: ClassRoomPostWriteItem) : RequestClassRoomPostData{
        return RequestClassRoomPostData(
            majorId = classRoomPostWriteItem.majorId,
            answererId = classRoomPostWriteItem.answererId,
            postTypeId = classRoomPostWriteItem.postTypeId,
            title = classRoomPostWriteItem.title,
            content = classRoomPostWriteItem.content
        )
    }

    // 1:1질문, 전체 질문, 정보글에 댓글 등록하는 데이터
    fun mapperToQuestionCommentWriteItem(questionCommentWriteItem: QuestionCommentWriteItem) : RequestQuestionCommentWriteData{
        return RequestQuestionCommentWriteData(
            postId = questionCommentWriteItem.postId,
            content = questionCommentWriteItem.content
        )
    }


}