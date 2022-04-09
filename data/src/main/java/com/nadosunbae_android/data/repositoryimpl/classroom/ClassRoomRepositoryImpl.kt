package com.nadosunbae_android.data.repositoryimpl.classroom

import com.nadosunbae_android.data.datasource.remote.classroom.ClassRoomDataSource
import com.nadosunbae_android.data.mapper.classroom.ClassRoomMapper
import com.nadosunbae_android.domain.model.classroom.*
import com.nadosunbae_android.domain.repository.classroom.ClassRoomRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

class ClassRoomRepositoryImpl(private val dataSource : ClassRoomDataSource) : ClassRoomRepository {
    //질문글 메인
    override suspend fun getClassRoomMain(
        postTypeId: Int,
        majorId: Int,
        sort: String
    ): List<ClassRoomData> {
        return ClassRoomMapper.mapperToQuestionMain(dataSource.getClassRoomMain(postTypeId, majorId, sort))
    }
    // 질문 상세
    override suspend fun getClassRoomQuestionDetail(postId: Int): QuestionDetailData {
        return ClassRoomMapper.mapperToQuestionDetailData(dataSource.getClassRoomQuestionDetail(postId))
    }
    //질문 작성
    override suspend fun postClassRoomWrite(classRoomPostWriteItem: ClassRoomPostWriteItem): ClassRoomPostWriteData {
        return ClassRoomMapper.mapperToClassRoomPostWriteData(dataSource.postClassRoomWrite(
            ClassRoomMapper.mapperToClassRoomPostWriteItem(classRoomPostWriteItem)
        ))
    }
    //구성원 전체보기
    override suspend fun getClassRoomSenior(majorId: Int): ClassRoomSeniorData {
        return ClassRoomMapper.mapperToSeniorData(dataSource.getClassRoomSenior(majorId))
    }

    // 1:1질문, 전체 질문, 정보글에 댓글 등록
    override suspend fun postQuestionCommentWrite(questionCommentWriteItem: QuestionCommentWriteItem): QuestionCommentWriteData {
        return ClassRoomMapper.mapperToQuestionCommentWriteData(dataSource.postQuestionCommentWrite(
            ClassRoomMapper.mapperToQuestionCommentWriteItem(questionCommentWriteItem)
        ))
    }

    // 선배 개인페이지
    override fun getSeniorPersonal(userId: Int): Flow<SeniorPersonalData> {
        return flow {
            // dataSource.getSeniorPersonal에서 수집한 뒤 이를 mapper로 변환하여 emit
            dataSource.getSeniorPersonal(userId).collect {
                emit(ClassRoomMapper.mapperToSeniorPersonalData(it))
            }
        }
    }

    // 선배 질문 리스트
    override fun getSeniorQuestionList(userId: Int, sort: String): Flow<List<ClassRoomData>> {
        return flow{
            dataSource.getSeniorQuestionList(userId, sort).collect {
                emit(ClassRoomMapper.mapperToSeniorQuestion(it))
            }

        }
    }

    // 정보글 상세
    override suspend fun getInformationDetail(postId: Int): InfoDetailData {
        return ClassRoomMapper.mapperToInfoDetailData(dataSource.getInformationDetail(postId))
    }

    override suspend fun putCommentUpdate(
        commentId: Int,
        commentUpdateItem: CommentUpdateItem
    ): CommentUpdateData {
        return ClassRoomMapper.mapperToCommentUpdateData(dataSource.putCommentUpdate(
            commentId,
            ClassRoomMapper.mapperToCommentUpdateItem(commentUpdateItem)
        ))
    }

    override suspend fun putWriteUpdate(
        postId: Int,
        writeUpdateItem: WriteUpdateItem
    ): WriteUpdateData {
        return ClassRoomMapper.mapperToWriteUpdateData(dataSource.putWriteUpdate(
            postId,
            ClassRoomMapper.mapperToWriteUpdateItem(writeUpdateItem)
        ))
    }

    // 1:1질문, 전체 질문 댓글 삭제
    override suspend fun deleteComment(commentId: Int): DeleteCommentData {
        return ClassRoomMapper.mapperToDeleteCommentData(dataSource.deleteComment(commentId))
    }

    //1:1 질문, 전체 질문, 정보글에 있는 원글 삭제
    override suspend fun deletePost(postId: Int): DeleteCommentData {
        return ClassRoomMapper.mapperToDeleteCommentData(dataSource.deletePost(postId))
    }


    override suspend fun postReport(reportItem: ReportItem): ReportData {
        return ClassRoomMapper.mapperToReportData(
            dataSource.postReport(ClassRoomMapper.mapperToRequestReportData(reportItem))
        )
    }
}