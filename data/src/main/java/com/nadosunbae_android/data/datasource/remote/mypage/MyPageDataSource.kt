package com.nadosunbae_android.data.datasource.remote.mypage

import com.nadosunbae_android.data.model.request.mypage.RequestMyPageBlockUpdate
import com.nadosunbae_android.data.model.request.mypage.RequestMyPageModify
import com.nadosunbae_android.data.model.request.mypage.RequestQuit
import com.nadosunbae_android.data.model.request.mypage.RequestResetPassword
import com.nadosunbae_android.data.model.response.mypage.*

interface MyPageDataSource {

    suspend fun getMyPageQuestion(userId: Int, sort: String = "recent") : ResponseMypageQuestionData

    suspend fun putMyPageModify(requestMyPageModify: RequestMyPageModify): ResponseMyPageModify

    suspend fun getMyPageVersion() : ResponseMyPageVersionData

    suspend fun postMyPageLogOut() : ResponseMyPageLogOut

    suspend fun getMyPageLikeReview(type: String = "review") : ResponseMyPageLikeReview

    suspend fun getMyPageLikeQuestion(type: String = "question") : ResponseMyPageLikeQuestion

    suspend fun getMyPageReview(userId: Int) : ResponseMyPageReviewData

    suspend fun getMyPageBlock(): ResponseMyPageBlock

    suspend fun postMyPageBlockUpdate(requestMyPageBlockUpdate: RequestMyPageBlockUpdate) : ResponseMyPageBlockUpdate

    suspend fun postResetPassword(requestResetPassword: RequestResetPassword) : ResponseResetPassword

    suspend fun deleteQuit(requestQuit: RequestQuit) : ResponseQuitData

}