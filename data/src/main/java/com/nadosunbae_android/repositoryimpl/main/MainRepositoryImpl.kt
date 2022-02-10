package com.nadosunbae_android.repositoryimpl.main

import com.nadosunbae_android.datasource.remote.main.MainDataSource
import com.nadosunbae_android.datasource.remote.main.MainDataSourceImpl
import com.nadosunbae_android.mapper.main.MainMapper
import com.nadosunbae_android.model.main.MajorData
import com.nadosunbae_android.model.response.major.ResponseMajorListData
import com.nadosunbae_android.repository.main.MainRepository
import retrofit2.Response

class MainRepositoryImpl(private val dataSource: MainDataSource) : MainRepository {

    override suspend fun getMajorList(universityId: Int, filter: String): List<MajorData> {
        return MainMapper.mapperToMajorData(dataSource.getMajorList(universityId, filter))
    }
}