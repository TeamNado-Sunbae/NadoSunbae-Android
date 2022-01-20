package com.nadosunbae_android.data.datasource.remote.mypage

import com.nadosunbae_android.data.model.response.mypage.ResponseMypageQuestionData
import retrofit2.Response

interface MyPageDataSource {

    fun getMyPageQuestion(
        userId : Int,
        sort : String,
        onResponse: (Response<ResponseMypageQuestionData>) -> Unit,
        onFailure: (Throwable) -> Unit)
}