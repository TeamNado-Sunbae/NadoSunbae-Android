package com.nadosunbae_android.data.datasource.remote.mypage

import com.nadosunbae_android.data.model.request.mypage.RequestMyPageModify
import com.nadosunbae_android.data.model.response.mypage.ResponseMyPageModify
import com.nadosunbae_android.data.model.response.mypage.ResponseMypageMyInfo
import com.nadosunbae_android.data.model.response.mypage.ResponseMypageQuestionData
import com.nadosunbae_android.domain.model.mypage.MyPageModifyItem

interface MyPageDataSource {

    suspend fun getMyPageQuestion(userId: Int, sort: String = "recent") : ResponseMypageQuestionData

    suspend fun getMyPageMyInfo(userId: Int) : ResponseMypageMyInfo

    suspend fun putMyPageModify(requestMyPageModify: MyPageModifyItem): ResponseMyPageModify
}