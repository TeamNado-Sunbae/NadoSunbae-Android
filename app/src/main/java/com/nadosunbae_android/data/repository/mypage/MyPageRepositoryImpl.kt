package com.nadosunbae_android.data.repository.mypage

import com.nadosunbae_android.data.api.ApiService
import com.nadosunbae_android.data.datasource.remote.mypage.MyPageDataSource
import com.nadosunbae_android.data.model.response.mypage.ResponseMypageQuestionData
import com.nadosunbae_android.util.enqueueUtil
import retrofit2.Response

class MyPageRepositoryImpl : MyPageRepository {
    override fun getMyPageQuestion(
        userId: Int,
        sort: String,
        onResponse: (Response<ResponseMypageQuestionData>) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        return ApiService.mypageService.getMyPageQuestion(userId, sort).enqueueUtil(
            onResponse, onFailure
        )
    }
}