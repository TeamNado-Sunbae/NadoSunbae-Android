package com.nadosunbae_android.data.datasource.remote.mypage

import com.nadosunbae_android.data.api.mypage.MyPageService
import com.nadosunbae_android.data.model.request.mypage.RequestMyPageModify
import com.nadosunbae_android.data.model.response.mypage.*

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

    override suspend fun getMyPagePost(type: String): ResponseMyPagePostData {
        return service.getPostByMe(type)
    }

    override suspend fun getMyPageReply(postTypeId: Int): ResponseMyPageReplyData {
        return service.getMyPageReply(postTypeId)
    }

    override suspend fun getMyPageVersion(): ResponseMyPageVersionData {
        return service.getMyPageVersion()
    }

    override suspend fun postMyPageLogOut(): ResponseMyPageLogOut {
        return service.postMyPageLogOut()
    }

    override suspend fun getMyPageLikeReview(type: String): ResponseMyPageLikeReview {
        return service.getMyPageLikePost(type)
    }

    override suspend fun getMyPageLikeQuestion(type: String): ResponseMyPageLikeQuestion {
        return service.getMyPageLikeQuestion(type)
    }

    override suspend fun getMyPageReview(userId: Int): ResponseMyPageReviewData {
        return service.getMyPageReview(userId)
    }

    override suspend fun getMyPageBlock(): ResponseMyPageBlock {
        return service.getMyPageBlock()
    }
}