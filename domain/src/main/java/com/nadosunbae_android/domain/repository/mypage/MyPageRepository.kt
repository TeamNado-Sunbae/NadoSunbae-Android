package com.nadosunbae_android.domain.repository.mypage

import com.nadosunbae_android.domain.model.mypage.*


interface MyPageRepository {
    suspend fun getMyPageQuestion(userId : Int, sort : String = "recent") : MyPageQuestionData

    suspend fun getMyPageMyInfo(userId: Int): MyPageMyInfo

    suspend fun putMyPageModify(myPageModifyItem: MyPageModifyItem) : MyPageModifyData

    suspend fun getMyPagePost(type: String = "question") : MyPagePostData

    suspend fun getMyPageReply(postTypeId: Int) : MyPageReplyData

    suspend fun getMyPageVersion() : MyPageVersionData

    suspend fun postMyPageLogOut() : MyPageLogOutData

    suspend fun getMyPageLikeReview(type: String = "review") : MyPageLikeReviewData

    suspend fun getMyPageLikeQuestion(type: String = "question") : MyPageLikeQuestionData

    suspend fun getMyPageReview(userId: Int) : MyPageReviewData

    suspend fun getMyPageBlock() : MyPageBlockData

    suspend fun postMyPageBlockUpdate(myPageBlockUpdateItem: MyPageBlockUpdateItem): MyPageBlockUpdateData

    suspend fun postMyPageResetPassword(myPageResetPasswordItem: MyPageResetPasswordItem) : MyPageResetPasswordData
}