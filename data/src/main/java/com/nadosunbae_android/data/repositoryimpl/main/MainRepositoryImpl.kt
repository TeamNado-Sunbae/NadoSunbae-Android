package com.nadosunbae_android.data.repositoryimpl.main

import com.nadosunbae_android.data.datasource.remote.main.MainDataSource
import com.nadosunbae_android.data.mapper.main.MainMapper
import com.nadosunbae_android.domain.model.main.MajorData
import com.nadosunbae_android.domain.repository.main.MainRepository

class MainRepositoryImpl(private val dataSource: MainDataSource) : MainRepository {

    override suspend fun getMajorList(universityId: Int, filter: String): List<MajorData> {
        return MainMapper.mapperToMajorData(dataSource.getMajorList(universityId, filter))
    }
}