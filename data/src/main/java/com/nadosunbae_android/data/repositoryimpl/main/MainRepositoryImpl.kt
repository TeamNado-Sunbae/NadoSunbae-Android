package com.nadosunbae_android.data.repositoryimpl.main

import com.nadosunbae_android.data.datasource.remote.main.MainDataSource
import com.nadosunbae_android.data.mapper.main.MainMapper
import com.nadosunbae_android.domain.model.main.AppLinkData
import com.nadosunbae_android.domain.model.main.MajorKeyData
import com.nadosunbae_android.domain.repository.main.MainRepository
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(private val dataSource: MainDataSource) : MainRepository {


    override suspend fun getMyPageAppLink(): AppLinkData {
        return MainMapper.mapperToLookUpLinkData(dataSource.getAppLink())
    }
}