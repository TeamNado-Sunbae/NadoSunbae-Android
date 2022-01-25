package com.nadosunbae_android.datasource.remote.mypage

import com.nadosunbae_android.model.response.mypage.ResponseMypageMyInfo
import com.nadosunbae_android.model.response.mypage.ResponseMypageQuestionData
import retrofit2.Response

interface MyPageDataSource {

    suspend fun getMyPageQuestion(
        userId : Int,
        sort : String) : ResponseMypageQuestionData

    suspend fun getMyPageMyInfo() : ResponseMypageMyInfo
}