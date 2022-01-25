package com.nadosunbae_android.datasource.remote.mypage

import com.nadosunbae_android.api.mypage.MyPageService
import com.nadosunbae_android.model.response.mypage.ResponseMypageMyInfo
import com.nadosunbae_android.model.response.mypage.ResponseMypageQuestionData

class MyPageDataSourceImpl(private val service : MyPageService) : MyPageDataSource {


    override suspend fun getMyPageQuestion(userId: Int, sort: String): ResponseMypageQuestionData {
        return service.getMyPageQuestion(userId, sort)
    }

    override suspend fun getMyPageMyInfo(): ResponseMypageMyInfo {
        return service.getMyPageMyInfo()
    }
}