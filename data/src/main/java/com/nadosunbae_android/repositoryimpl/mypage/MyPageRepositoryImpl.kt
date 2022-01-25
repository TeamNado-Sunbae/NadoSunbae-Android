package com.nadosunbae_android.repositoryimpl.mypage

import com.nadosunbae_android.api.ApiService
import com.nadosunbae_android.model.response.mypage.ResponseMypageMyInfo
import com.nadosunbae_android.model.response.mypage.ResponseMypageQuestionData
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

    override fun getMyPageMyInfo(
        onResponse: (Response<ResponseMypageMyInfo>) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        return ApiService.mypageService.getMyPageMyInfo().enqueueUtil(
            onResponse, onFailure
        )
    }
}