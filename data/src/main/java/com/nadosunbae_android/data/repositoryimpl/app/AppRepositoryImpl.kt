package com.nadosunbae_android.data.repositoryimpl.app

import com.nadosunbae_android.data.datasource.remote.app.AppDataSource
import com.nadosunbae_android.data.mapper.app.AppMapper
import com.nadosunbae_android.domain.model.app.AppBannerData
import com.nadosunbae_android.domain.repository.app.AppRepository
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    private val dataSource: AppDataSource
): AppRepository {
    override suspend fun getAppBanner(type: String): List<AppBannerData> {
        return AppMapper.mapperToAppBanner(dataSource.getAppBanner(type))
    }

}