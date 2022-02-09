package com.nadosunbae_android.repositoryimpl.mypage

import com.nadosunbae_android.datasource.remote.mypage.MyPageDataSource
import com.nadosunbae_android.mapper.mypage.MypageMapper
import com.nadosunbae_android.model.mypage.MyPageMyInfo
import com.nadosunbae_android.model.mypage.MyPageQuestionData
import com.nadosunbae_android.repository.mypage.MyPageRepository

class MyPageRepositoryImpl(private val dataSource: MyPageDataSource) : MyPageRepository {

    override suspend fun getMyPageQuestion(userId: Int, sort: String) : MyPageQuestionData{
        return MypageMapper.mapperToQuestion(dataSource.getMyPageQuestion(userId, sort))
    }

    override suspend fun getMyPageMyInfo() : MyPageMyInfo {
        return MypageMapper.mapperToMyInfo(dataSource.getMyPageMyInfo())
    }
}