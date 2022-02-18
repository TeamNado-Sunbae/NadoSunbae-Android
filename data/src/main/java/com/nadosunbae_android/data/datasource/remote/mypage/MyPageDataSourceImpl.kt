package com.nadosunbae_android.data.datasource.remote.mypage

import com.nadosunbae_android.data.api.mypage.MyPageService
import com.nadosunbae_android.data.model.request.mypage.RequestMyPageModify
import com.nadosunbae_android.data.model.response.mypage.ResponseMyPageModify
import com.nadosunbae_android.data.model.response.mypage.ResponseMypageMyInfo
import com.nadosunbae_android.data.model.response.mypage.ResponseMypageQuestionData

class MyPageDataSourceImpl(private val service : MyPageService) : MyPageDataSource {


    override suspend fun getMyPageQuestion(userId: Int, sort: String): ResponseMypageQuestionData {
        return service.getMyPageQuestion(userId, sort)
    }

    override suspend fun getMyPageMyInfo(userId: Int): ResponseMypageMyInfo {
        return service.getMyPageMyInfo(userId)
    }

    override suspend fun putMyPageModify(requestMyPageModify: RequestMyPageModify): ResponseMyPageModify {
        return service.putMyPageModify(requestMyPageModify)
    }
}