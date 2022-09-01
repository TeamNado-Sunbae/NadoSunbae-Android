package com.nadosunbae_android.data.repositoryimpl.community

import com.nadosunbae_android.data.datasource.remote.community.CommunityDataSource
import com.nadosunbae_android.data.model.response.community.toEntity
import com.nadosunbae_android.domain.model.community.CommunityMainData
import com.nadosunbae_android.domain.repository.community.CommunityRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

import javax.inject.Inject

class CommunityRepositoryImpl @Inject constructor(
    val dataSource: CommunityDataSource
) : CommunityRepository {

    override fun getCommunityMain(
        majorId: String,
        filter: String,
        sort: String
    ): Flow<List<CommunityMainData>> = flow {
        emit(dataSource.getCommunityMain(majorId, filter, sort)
            .data
            .map { it.toEntity() })
    }.flowOn(Dispatchers.IO)
}