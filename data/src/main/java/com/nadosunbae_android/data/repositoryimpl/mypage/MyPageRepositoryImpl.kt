package com.nadosunbae_android.data.repositoryimpl.mypage

import com.nadosunbae_android.data.datasource.remote.mypage.MyPageDataSource
import com.nadosunbae_android.data.mapper.mypage.MypageMapper
import com.nadosunbae_android.domain.model.mypage.*
import com.nadosunbae_android.domain.repository.mypage.MyPageRepository

class MyPageRepositoryImpl(private val dataSource: MyPageDataSource) : MyPageRepository {

    override suspend fun getMyPageQuestion(userId: Int, sort: String) : MyPageQuestionData {
        return MypageMapper.mapperToQuestion(dataSource.getMyPageQuestion(userId, sort))
    }

    override suspend fun getMyPageMyInfo(userId: Int) : MyPageMyInfo {
        return MypageMapper.mapperToMyInfo(dataSource.getMyPageMyInfo(userId))
    }

    override suspend fun putMyPageModify(myPageModifyItem: MyPageModifyItem): MyPageModifyData {
        return MypageMapper.mapperToModifyData(dataSource.putMyPageModify(
            MypageMapper.mapperToModifyItem(myPageModifyItem)
        ))
    }

    override suspend fun getMyPagePost(type: String): MyPagePostData {
        return MypageMapper.mapperToPost(dataSource.getMyPagePost(type))
    }

    override suspend fun getMyPageReply(postTypeId: Int): MyPageReplyData {
        return MypageMapper.mapperToReply(dataSource.getMyPageReply(postTypeId))
    }

    override suspend fun getMyPageVersion(): MyPageVersionData {
        return MypageMapper.mapperToVersion(dataSource.getMyPageVersion())
    }

    override suspend fun postMyPageLogOut(): MyPageLogOutData {
        return MypageMapper.mapperToLogOut(dataSource.postMyPageLogOut())
    }

    override suspend fun getMyPageLikeReview(type: String): MyPageLikeReviewData {
        return MypageMapper.mapperToLikeListReview(dataSource.getMyPageLikeReview(type))
    }

    override suspend fun getMyPageLikeQuestion(type: String): MyPageLikeQuestionData {
        return MypageMapper.mapperToLikeListQuestion(dataSource.getMyPageLikeQuestion(type))
    }

}