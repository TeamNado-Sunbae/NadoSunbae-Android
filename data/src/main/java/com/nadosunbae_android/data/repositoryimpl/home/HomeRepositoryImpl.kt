package com.nadosunbae_android.data.repositoryimpl.home

import com.nadosunbae_android.data.datasource.remote.home.HomeDataSource
import com.nadosunbae_android.data.mapper.home.HomeMapper
import com.nadosunbae_android.domain.model.home.HomeRankingData
import com.nadosunbae_android.domain.model.home.HomeUnivReviewData
import com.nadosunbae_android.domain.repository.home.HomeRepository

class HomeRepositoryImpl(private val dataSource: HomeDataSource) : HomeRepository {
    override suspend fun getUnivReview(university: Int): List<HomeUnivReviewData> {
        return HomeMapper.mapperToUnviReview(dataSource.getUnviReview(university))
    }

    override suspend fun getHomeRanking(university: Int): List<HomeRankingData> {
        return HomeMapper.mapperToRanking(dataSource.getHomeRanking(university))
    }
}