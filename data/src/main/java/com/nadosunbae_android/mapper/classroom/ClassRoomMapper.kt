package com.nadosunbae_android.mapper.classroom

import com.nadosunbae_android.model.classroom.*
import com.nadosunbae_android.model.mypage.MyPageQuestionData
import com.nadosunbae_android.model.request.classroom.RequestClassRoomPostData
import com.nadosunbae_android.model.request.classroom.RequestQuestionCommentWriteData
import com.nadosunbae_android.model.response.classroom.*

object ClassRoomMapper {
    //QuestionFragment
    fun mapperToQuestionMain(responseClassRoomMainData: ResponseClassRoomMainData): List<ClassRoomData> {
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
    fun mapperToSeniorData(responseClassRoomSeniorData: ResponseClassRoomSeniorData): ClassRoomSeniorData {
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
    fun mapperToSeniorQuestion(responseSeniorQuestionData: ResponseSeniorQuestionData): List<ClassRoomData> {
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
    fun mapperToMyPageQuestion(responseMypageQuestionData: MyPageQuestionData): List<ClassRoomData> {
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
    fun mapperToQuestionDetailData(responseClassRoomQuestionDetail: ResponseClassRoomQuestionDetail): QuestionDetailData {
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
                    it.writer.writerId
                )
            },
            questionerId = responseClassRoomQuestionDetail.data.questionerId
        )
    }

    // 1:1질문, 전체질문, 정보글 등록 받는 데이터
    fun mapperToClassRoomPostWriteData(responseClassRoomWriteData: ResponseClassRoomWriteData): ClassRoomPostWriteData {
        return ClassRoomPostWriteData(
            success = responseClassRoomWriteData.success,
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
    fun mapperToClassRoomPostWriteItem(classRoomPostWriteItem: ClassRoomPostWriteItem): RequestClassRoomPostData {
        return RequestClassRoomPostData(
            majorId = classRoomPostWriteItem.majorId,
            answererId = classRoomPostWriteItem.answererId,
            postTypeId = classRoomPostWriteItem.postTypeId,
            title = classRoomPostWriteItem.title,
            content = classRoomPostWriteItem.content
        )
    }

    // 1:1질문, 전체 질문, 정보글에 댓글 등록하는 데이터
    fun mapperToQuestionCommentWriteItem(questionCommentWriteItem: QuestionCommentWriteItem): RequestQuestionCommentWriteData {
        return RequestQuestionCommentWriteData(
            postId = questionCommentWriteItem.postId,
            content = questionCommentWriteItem.content
        )
    }

    // 1:1질문, 전체 질문, 정보글에 댓글 등록 받는 데이터
    fun mapperToQuestionCommentWriteData(responseQuestionCommentWrite: ResponseQuestionCommentWrite): QuestionCommentWriteData {
        return QuestionCommentWriteData(
            success = responseQuestionCommentWrite.success,
            commentId = responseQuestionCommentWrite.data.commentId,
            content = responseQuestionCommentWrite.data.content,
            createdAt = responseQuestionCommentWrite.data.createdAt,
            isDeleted = responseQuestionCommentWrite.data.isDeleted,
            postId = responseQuestionCommentWrite.data.postId,
            firstMajorName = responseQuestionCommentWrite.data.writer.firstMajorName,
            firstMajorStart = responseQuestionCommentWrite.data.writer.firstMajorStart,
            nickname = responseQuestionCommentWrite.data.writer.nickname,
            profileImageId = responseQuestionCommentWrite.data.writer.profileImageId,
            secondMajorName = responseQuestionCommentWrite.data.writer.secondMajorName,
            secondMajorStart = responseQuestionCommentWrite.data.writer.secondMajorStart,
            writerId = responseQuestionCommentWrite.data.writer.writerId
        )
    }


    // 정보글 상세 조회
    fun mapperToInfoDetailData(responseInfoDetailData: ResponseInfoDetailData): InfoDetailData {
        return InfoDetailData(
            commentCount = responseInfoDetailData.data.commentCount,
            isLiked = responseInfoDetailData.data.like.isLiked,
            likeCount = responseInfoDetailData.data.like.likeCount,
            content = responseInfoDetailData.data.post.content,
            createdAt = responseInfoDetailData.data.post.createdAt,
            postId = responseInfoDetailData.data.post.postId,
            title = responseInfoDetailData.data.post.title,
            firstMajorName = responseInfoDetailData.data.writer.firstMajorName,
            firstMajorStart = responseInfoDetailData.data.writer.firstMajorStart,
            nickname = responseInfoDetailData.data.writer.nickname,
            profileImageId = responseInfoDetailData.data.writer.profileImageId,
            secondMajorName = responseInfoDetailData.data.writer.secondMajorName,
            secondMajorStart = responseInfoDetailData.data.writer.secondMajorStart,
            writerId = responseInfoDetailData.data.writer.writerId,
            commentList = responseInfoDetailData.data.commentList.map {
                InfoDetailData.Comment(
                    commentId = it.commentId,
                    content = it.content,
                    createdAt = it.createdAt,
                    isDeleted = it.isDeleted,
                    firstMajorName = it.writer.firstMajorName,
                    firstMajorStart = it.writer.firstMajorStart,
                    isPostWriter = it.writer.isPostWriter,
                    nickname = it.writer.nickname,
                    profileImageId = it.writer.profileImageId,
                    secondMajorName = it.writer.secondMajorName,
                    secondMajorStart = it.writer.secondMajorStart,
                    writerId = it.writer.writerId
                )
            }
        )
    }

    // 선배 개인페이지 정보
    fun mapperToSeniorPersonalData(responseSeniorPersonalData: ResponseSeniorPersonalData) : SeniorPersonalData{
        return SeniorPersonalData(
            firstMajorName = responseSeniorPersonalData.data.firstMajorName,
        firstMajorStart = responseSeniorPersonalData.data.firstMajorStart,
        isOnQuestion = responseSeniorPersonalData.data.isOnQuestion,
        nickname = responseSeniorPersonalData.data.nickname,
        profileImageId = responseSeniorPersonalData.data.profileImageId,
        secondMajorName = responseSeniorPersonalData.data.secondMajorName,
        secondMajorStart = responseSeniorPersonalData.data.secondMajorStart,
        userId = responseSeniorPersonalData.data.userId
        )

    }
}