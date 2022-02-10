package com.nadosunbae_android.data.datasource.remote.mypage

import com.nadosunbae_android.data.model.response.mypage.ResponseMypageMyInfo
import com.nadosunbae_android.data.model.response.mypage.ResponseMypageQuestionData

interface MyPageDataSource {

    suspend fun getMyPageQuestion(userId: Int, sort: String) : ResponseMypageQuestionData

    suspend fun getMyPageMyInfo() : ResponseMypageMyInfo
}