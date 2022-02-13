package com.nadosunbae_android.data.repositoryimpl.mypage

import com.nadosunbae_android.data.datasource.remote.mypage.MyPageDataSource
import com.nadosunbae_android.data.mapper.mypage.MypageMapper
import com.nadosunbae_android.domain.model.mypage.MyPageMyInfo
import com.nadosunbae_android.domain.model.mypage.MyPageQuestionData
import com.nadosunbae_android.domain.repository.mypage.MyPageRepository

class MyPageRepositoryImpl(private val dataSource: MyPageDataSource) : MyPageRepository {

    override suspend fun getMyPageQuestion(userId: Int, sort: String) : MyPageQuestionData{
        return MypageMapper.mapperToQuestion(dataSource.getMyPageQuestion(userId, sort))
    }

    override suspend fun getMyPageMyInfo(userId: Int) : MyPageMyInfo {
        return MypageMapper.mapperToMyInfo(dataSource.getMyPageMyInfo(userId))
    }
}