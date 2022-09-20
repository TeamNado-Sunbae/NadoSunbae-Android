package com.nadosunbae_android.data.mapper.classroom

import com.nadosunbae_android.data.model.request.classroom.*
import com.nadosunbae_android.domain.model.classroom.*
import com.nadosunbae_android.data.model.response.classroom.*

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
                isLiked = it.like.isLiked,
                likeCount = it.like.likeCount,
                commentCount = it.commentCount
            )
        }
    }

    //구성원 전체보기 선배 데이터
    fun mapperToSeniorData(responseClassRoomSeniorData: ResponseClassRoomSeniorData): ClassRoomSeniorData {
        return ClassRoomSeniorData(
            userSummaryDataList = responseClassRoomSeniorData.data.offQuestionUserList.map {
                ClassRoomSeniorData.UserSummaryData(
                    it.profileImageId,
                    it.isFirstMajor,
                    it.isOnQuestion,
                    it.majorStart,
                    it.nickname,
                    it.userId,
                    it.rate
                )
            },
            onQuestionUserList = responseClassRoomSeniorData.data.onQuestionUserList.map {
                ClassRoomSeniorData.UserSummaryData(
                    it.profileImageId,
                    it.isFirstMajor,
                    it.isOnQuestion,
                    it.majorStart,
                    it.nickname,
                    it.userId,
                    it.rate
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
                isLiked = it.like.isLiked,
                likeCount = it.like.likeCount,
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
    fun mapperToSeniorPersonalData(responseSeniorPersonalData: ResponseSeniorPersonalData): SeniorPersonalData {
        return SeniorPersonalData(
            firstMajorName = responseSeniorPersonalData.data.firstMajorName,
            firstMajorStart = responseSeniorPersonalData.data.firstMajorStart,
            isOnQuestion = responseSeniorPersonalData.data.isOnQuestion,
            nickname = responseSeniorPersonalData.data.nickname,
            profileImageId = responseSeniorPersonalData.data.profileImageId,
            secondMajorName = responseSeniorPersonalData.data.secondMajorName,
            secondMajorStart = responseSeniorPersonalData.data.secondMajorStart,
            userId = responseSeniorPersonalData.data.userId,
            count = responseSeniorPersonalData.data.count
        )
    }

    //1:1 질문, 전체 질문, 정보글에 있는 특정 댓글 수정 Request
    fun mapperToCommentUpdateItem(commentUpdateItem: CommentUpdateItem): RequestCommentUpdateData {
        return RequestCommentUpdateData(
            content = commentUpdateItem.content
        )
    }

    //1:1 질문, 전체 질문, 정보글에 있는 특정 댓글 수정 Response
    fun mapperToCommentUpdateData(responseCommentUpdateData: ResponseCommentUpdateData): CommentUpdateData {
        return CommentUpdateData(
            commentId = responseCommentUpdateData.data.commentId,
            content = responseCommentUpdateData.data.content,
            createdAt = responseCommentUpdateData.data.createdAt,
            isDeleted = responseCommentUpdateData.data.isDeleted,
            postId = responseCommentUpdateData.data.postId,
            updatedAt = responseCommentUpdateData.data.updatedAt,
            firstMajorName = responseCommentUpdateData.data.writer.firstMajorName,
            firstMajorStart = responseCommentUpdateData.data.writer.firstMajorStart,
            nickname = responseCommentUpdateData.data.writer.nickname,
            profileImageId = responseCommentUpdateData.data.writer.profileImageId,
            secondMajorName = responseCommentUpdateData.data.writer.secondMajorName,
            secondMajorStart = responseCommentUpdateData.data.writer.secondMajorStart,
            writerId = responseCommentUpdateData.data.writer.writerId
        )
    }

    //1:1 질문, 전체 질문, 정보글에 있는 원글 Request
    fun mapperToWriteUpdateItem(writeUpdateItem: WriteUpdateItem): RequestWriteUpdateData {
        return RequestWriteUpdateData(
            title = writeUpdateItem.title,
            content = writeUpdateItem.content
        )
    }

    fun mapperToWriteUpdateData(responseWriteUpdateData: ResponseWriteUpdateData): WriteUpdateData {
        return WriteUpdateData(
            success = responseWriteUpdateData.success,
            isLiked = responseWriteUpdateData.data.like.isLiked,
            likeCount = responseWriteUpdateData.data.like.likeCount,
            content = responseWriteUpdateData.data.post.content,
            createdAt = responseWriteUpdateData.data.post.createdAt,
            postId = responseWriteUpdateData.data.post.postId,
            title = responseWriteUpdateData.data.post.title,
            updatedAt = responseWriteUpdateData.data.post.updatedAt,
            firstMajorName = responseWriteUpdateData.data.writer.firstMajorName,
            firstMajorStart = responseWriteUpdateData.data.writer.firstMajorStart,
            nickname = responseWriteUpdateData.data.writer.nickname,
            profileImageId = responseWriteUpdateData.data.writer.profileImageId,
            secondMajorName = responseWriteUpdateData.data.writer.secondMajorName,
            secondMajorStart = responseWriteUpdateData.data.writer.secondMajorStart,
            writerId = responseWriteUpdateData.data.writer.writerId
        )
    }

    // 1:1질문, 전체 질문 댓글 삭제
    fun mapperToDeleteCommentData(responseDeleteComment: ResponseDeleteComment) : DeleteCommentData{
        return DeleteCommentData(
            commentId = responseDeleteComment.data.commentId,
            isDeleted = responseDeleteComment.data.isDeleted
        )
    }

    //신고 요청
    fun mapperToRequestReportData(reportItem: ReportItem) : RequestReportData{
        return RequestReportData(
            reportedTargetId = reportItem.reportedTargetId,
        reportedTargetTypeId = reportItem.reportedTargetTypeId,
        reason = reportItem.reason
        )
    }

    //신고 요청 응답
    fun mapperToReportData(responseReportData: ResponseReportData) : ReportData{
        return ReportData(
            createdAt = responseReportData.data.createdAt,
            reportId = responseReportData.data.reportId
        )
    }
 }