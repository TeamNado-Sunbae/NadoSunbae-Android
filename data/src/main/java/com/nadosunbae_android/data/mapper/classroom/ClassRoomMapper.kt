package com.nadosunbae_android.data.mapper.classroom

import com.nadosunbae_android.data.model.request.classroom.RequestReportData
import com.nadosunbae_android.data.model.request.classroom.RequestWriteUpdateData
import com.nadosunbae_android.data.model.response.classroom.*
import com.nadosunbae_android.domain.model.classroom.*

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
    fun mapperToDeleteCommentData(responseDeleteComment: ResponseDeleteComment): DeleteCommentData {
        return DeleteCommentData(
            commentId = responseDeleteComment.data.commentId,
            isDeleted = responseDeleteComment.data.isDeleted
        )
    }

    //신고 요청
    fun mapperToRequestReportData(reportItem: ReportItem): RequestReportData {
        return RequestReportData(
            reportedTargetId = reportItem.reportedTargetId,
            reportedTargetTypeId = reportItem.reportedTargetTypeId,
            reason = reportItem.reason
        )
    }

    //신고 요청 응답
    fun mapperToReportData(responseReportData: ResponseReportData): ReportData {
        return ReportData(
            createdAt = responseReportData.data.createdAt,
            reportId = responseReportData.data.reportId
        )
    }
}