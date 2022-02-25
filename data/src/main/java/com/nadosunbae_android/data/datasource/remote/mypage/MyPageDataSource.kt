package com.nadosunbae_android.data.datasource.remote.mypage

import com.nadosunbae_android.data.model.request.mypage.RequestMyPageModify
import com.nadosunbae_android.data.model.response.mypage.*

interface MyPageDataSource {

    suspend fun getMyPageQuestion(userId: Int, sort: String = "recent") : ResponseMypageQuestionData

    suspend fun getMyPageMyInfo(userId: Int) : ResponseMypageMyInfo

    suspend fun putMyPageModify(requestMyPageModify: RequestMyPageModify): ResponseMyPageModify

    suspend fun getMyPagePost(type: String = "question") : ResponseMyPagePostData

    suspend fun getMyPageReply(postTypeId: Int) : ResponseMyPageReplyData

    suspend fun getMyPageVersion() : ResponseMyPageVersionData

    suspend fun postMyPageLogOut() : ResponseMyPageLogOut

    suspend fun getMyPageLikeReview(type: String = "review") : ResponseMyPageLikeReview

    suspend fun getMyPageLikeQuestion(type: String = "question") : ResponseMyPageLikeQuestion

    suspend fun getMyPageReview(userId: Int) : ResponseMyPageReviewData

    suspend fun getMyPageBlock(): ResponseMyPageBlock
}