package com.nadosunbae_android.repository.mypage

import com.nadosunbae_android.model.response.mypage.ResponseMypageMyInfo
import com.nadosunbae_android.model.response.mypage.ResponseMypageQuestionData
import retrofit2.Response

interface MyPageRepository {
    fun getMyPageQuestion(
        userId : Int,
        sort : String,
        onResponse: (Response<ResponseMypageQuestionData>) -> Unit,
        onFailure: (Throwable) -> Unit)

    fun getMyPageMyInfo(
        onResponse: (Response<ResponseMypageMyInfo>) -> Unit,
        onFailure: (Throwable) -> Unit)
}