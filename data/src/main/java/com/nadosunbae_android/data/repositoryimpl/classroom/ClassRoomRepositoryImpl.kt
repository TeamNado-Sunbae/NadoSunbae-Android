package com.nadosunbae_android.data.repositoryimpl.classroom

import com.nadosunbae_android.data.datasource.remote.classroom.ClassRoomDataSource
import com.nadosunbae_android.data.mapper.classroom.ClassRoomMapper
import com.nadosunbae_android.domain.model.classroom.*
import com.nadosunbae_android.domain.repository.classroom.ClassRoomRepository
import javax.inject.Inject

class ClassRoomRepositoryImpl @Inject constructor(
    private val dataSource: ClassRoomDataSource
) : ClassRoomRepository {
    //질문글 메인
    override suspend fun getClassRoomMain(
        postTypeId: Int,
        majorId: Int,
        sort: String
    ): List<ClassRoomData> {
        return ClassRoomMapper.mapperToQuestionMain(
            dataSource.getClassRoomMain(
                postTypeId,
                majorId,
                sort
            )
        )
    }

    override suspend fun putWriteUpdate(
        postId: Int,
        writeUpdateItem: WriteUpdateItem
    ): WriteUpdateData {
        return ClassRoomMapper.mapperToWriteUpdateData(
            dataSource.putWriteUpdate(
                postId,
                ClassRoomMapper.mapperToWriteUpdateItem(writeUpdateItem)
            )
        )
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