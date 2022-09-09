package com.nadosunbae_android.data.repositoryimpl.major

import com.nadosunbae_android.data.datasource.remote.major.MajorDataSource
import com.nadosunbae_android.data.model.response.major.toEntity
import com.nadosunbae_android.domain.model.major.MajorListData
import com.nadosunbae_android.domain.repository.major.MajorRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class MajorRepositoryImpl(private val dataSource: MajorDataSource) : MajorRepository {
    override fun getMajorList(
        universityId: String,
        filter: String,
        exclude: String
    ): Flow<List<MajorListData>> = flow {
        emit(dataSource.getMajorList(universityId, filter, exclude)
            .data
            .map { it.toEntity() })
    }.flowOn(Dispatchers.IO)
}