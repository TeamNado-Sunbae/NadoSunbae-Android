package com.nadosunbae_android.data.repositoryimpl.classroom

import com.nadosunbae_android.data.datasource.remote.classroom.ClassRoomDataSource
import com.nadosunbae_android.data.mapper.classroom.ClassRoomMapper
import com.nadosunbae_android.data.model.classroom.*
import com.nadosunbae_android.domain.repository.classroom.ClassRoomRepository

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
    override suspend fun getSeniorPersonal(userId: Int): SeniorPersonalData {
        return ClassRoomMapper.mapperToSeniorPersonalData(dataSource.getSeniorPersonal(userId))
    }

    // 선배 질문 리스트
    override suspend fun getSeniorQuestionList(userId: Int, sort: String): List<ClassRoomData> {
        return ClassRoomMapper.mapperToSeniorQuestion(dataSource.getSeniorQuestionList(userId, sort))
    }

    // 정보글 상세
    override suspend fun getInformationDetail(postId: Int): InfoDetailData {
        return ClassRoomMapper.mapperToInfoDetailData(dataSource.getInformationDetail(postId))
    }

}