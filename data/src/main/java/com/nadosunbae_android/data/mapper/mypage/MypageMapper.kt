package com.nadosunbae_android.data.mapper.mypage

import com.nadosunbae_android.data.model.request.mypage.RequestMyPageModify
import com.nadosunbae_android.data.model.response.mypage.ResponseMyPageModify
import com.nadosunbae_android.data.model.response.mypage.ResponseMyPagePostData
import com.nadosunbae_android.data.model.response.mypage.ResponseMypageMyInfo
import com.nadosunbae_android.data.model.response.mypage.ResponseMypageQuestionData
import com.nadosunbae_android.domain.model.mypage.*

object MypageMapper {

    //일대일 질문
    fun mapperToQuestion(responseMypageQuestionData: ResponseMypageQuestionData): MyPageQuestionData {
        return MyPageQuestionData(
            data = MyPageQuestionData.Data(
                classroomPostList = responseMypageQuestionData.data.classroomPostList.map {
                    MyPageQuestionData.Data.ClassroomPost(
                        commentCount = it.commentCount,
                        content = it.content,
                        createdAt = it.createdAt,
                        like = MyPageQuestionData.Data.ClassroomPost.Like(
                            isLiked = it.like.isLiked,
                            likeCount = it.like.likeCount
                        ),
                        postId = it.postId,
                        title = it.title,
                        writer = MyPageQuestionData.Data.ClassroomPost.Writer(
                            nickname = it.writer.nickname,
                            profileImageId = it.writer.profileImageId,
                            writerId = it.writer.writerId
                        )
                    )

                }

            ),
            success = responseMypageQuestionData.success
        )

    }

    // 내 정보
    fun mapperToMyInfo(responseMypageMyInfo: ResponseMypageMyInfo): MyPageMyInfo {
        return MyPageMyInfo(
            data = MyPageMyInfo.Data(
                count = responseMypageMyInfo.data.count,
                firstMajorName = responseMypageMyInfo.data.firstMajorName,
                firstMajorStart = responseMypageMyInfo.data.firstMajorStart,
                isOnQuestion = responseMypageMyInfo.data.isOnQuestion,
                nickname = responseMypageMyInfo.data.nickname,
                profileImageId = responseMypageMyInfo.data.profileImageId,
                secondMajorName = responseMypageMyInfo.data.secondMajorName,
                secondMajorStart = responseMypageMyInfo.data.secondMajorStart,
                userId = responseMypageMyInfo.data.userId
            ),
            success = responseMypageMyInfo.success
        )
    }

    // 내 정보 수정
    fun mapperToModifyData(responseMyPageModify: ResponseMyPageModify): MyPageModifyData {
        return MyPageModifyData(
            data = MyPageModifyData.Data(
                firstMajorId = responseMyPageModify.data.firstMajorId,
                firstMajorStart = responseMyPageModify.data.firstMajorStart,
                isOnQuestion = responseMyPageModify.data.isOnQuestion,
                nickname = responseMyPageModify.data.nickname,
                secondMajorId = responseMyPageModify.data.secondMajorId,
                secondMajorStart = responseMyPageModify.data.secondMajorStart,
                updatedAt = responseMyPageModify.data.updatedAt
            ),
            success = responseMyPageModify.success
        )
    }

    fun mapperToModifyItem(myPageModifyItem: MyPageModifyItem): RequestMyPageModify {
        return RequestMyPageModify(
            nickname = myPageModifyItem.nickname,
            firstMajorId = myPageModifyItem.firstMajorId,
            firstMajorStart = myPageModifyItem.firstMajorStart,
            secondMajorId = myPageModifyItem.secondMajorId,
            secondMajorStart = myPageModifyItem.secondMajorStart,
            isOnQuestion = myPageModifyItem.isOnQuestion
        )
    }

    //내가 쓴 글
    fun mapperToPost(responseMyPagePostData: ResponseMyPagePostData): MyPagePostData {
        return MyPagePostData(
            data = MyPagePostData.Data(
                classroomPostList = responseMyPagePostData.data.classroomPostList.map {
                    MyPagePostData.Data.ClassroomPost(
                        commentCount = it.commentCount,
                        content = it.content,
                        createdAt = it.createdAt,
                        like = MyPagePostData.Data.ClassroomPost.Like(
                            isLiked = it.like.isLiked,
                            likeCount = it.like.likeCount
                        ),
                        majorName = it.majorName,
                        postId = it.postId,
                        title = it.title
                    )
                }
            ),
            success = responseMyPagePostData.success
        )
    }
}