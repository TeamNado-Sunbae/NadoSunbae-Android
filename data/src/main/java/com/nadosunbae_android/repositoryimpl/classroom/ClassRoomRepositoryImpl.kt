package com.nadosunbae_android.repositoryimpl.classroom

import com.nadosunbae_android.datasource.remote.classroom.ClassRoomDataSource
import com.nadosunbae_android.datasource.remote.classroom.ClassRoomDataSourceImpl
import com.nadosunbae_android.mapper.classroom.ClassRoomMapper
import com.nadosunbae_android.model.classroom.*
import com.nadosunbae_android.model.request.classroom.RequestClassRoomPostData
import com.nadosunbae_android.model.request.classroom.RequestQuestionCommentWriteData
import com.nadosunbae_android.model.response.classroom.*
import com.nadosunbae_android.repository.classroom.ClassRoomRepository
import retrofit2.Response

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
        return
    }

    override suspend fun getSeniorPersonal(userId: Int): QuestionCommentWriteData {
        TODO("Not yet implemented")
    }

    override suspend fun getSeniorQuestionList(userId: Int, sort: String): ClassRoomData {
        TODO("Not yet implemented")
    }

    override suspend fun getInformationDetail(postId: Int): InfoDetailData {
        TODO("Not yet implemented")
    }


    override fun getClassRoomMain(
        postTypeId: Int,
        majorId: Int,
        sort: String,
        onResponse: (Response<ResponseClassRoomMainData>) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        return classRoomDataSource.getClassRoomMain(postTypeId, majorId, sort, onResponse, onFailure)
    }

    override fun getClassRoomQuestionDetail(
        postId: Int,
        onResponse: (Response<ResponseClassRoomQuestionDetail>) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        return classRoomDataSource.getClassRoomQuestionDetail(postId,onResponse, onFailure)
    }

    // 작성하기
    override fun postClassRoomWrite(
        requestClassRoomPostData: RequestClassRoomPostData,
        onResponse: (Response<ResponseClassRoomWriteData>) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        return classRoomDataSource.postClassRoomWrite(requestClassRoomPostData, onResponse, onFailure)
    }


    //구성원 전체 보기
    override fun getClassRoomSenior(
        majorId: Int,
        onResponse: (Response<ResponseClassRoomSeniorData>) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        return classRoomDataSource.getClassRoomSenior(majorId, onResponse, onFailure)
    }

    override fun postQuestionCommentWrite(
        requestQuestionCommentWriteData: RequestQuestionCommentWriteData,
        onResponse: (Response<ResponseQuestionCommentWrite>) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        return classRoomDataSource.postQuestionCommentWrite(requestQuestionCommentWriteData, onResponse, onFailure)
    }


    //선배 개인페이지
    override fun getSeniorPersonal(
        userId: Int,
        onResponse: (Response<ResponseSeniorPersonalData>) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        return classRoomDataSource.getSeniorPersonal(userId, onResponse, onFailure)
    }

    //선배 1:1 질문 리스트
    override fun getSeniorQuestionList(
        userId: Int,
        sort: String,
        onResponse: (Response<ResponseSeniorQuestionData>) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        return classRoomDataSource.getSeniorQuestionList(userId, sort, onResponse, onFailure)
    }

    //정보 상세 조회
    override fun getInformationDetail(
        postId: Int,
        onResponse: (Response<ResponseInfoDetailData>) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        return classRoomDataSource.getInformationDetail(postId, onResponse, onFailure)
    }

}