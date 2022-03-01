package com.nadosunbae_android.data.mapper.mypage

import androidx.lifecycle.Transformations.map
import com.nadosunbae_android.data.model.request.mypage.RequestMyPageBlockUpdate
import com.nadosunbae_android.data.model.request.mypage.RequestMyPageModify
import com.nadosunbae_android.data.model.request.mypage.RequestResetPassword
import com.nadosunbae_android.data.model.response.mypage.*
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

    //내가 쓴 답글
    fun mapperToReply(responseMyPageReplyData: ResponseMyPageReplyData): MyPageReplyData {
        return MyPageReplyData(
            data = MyPageReplyData.Data(
                classroomPostListByMyCommentList = responseMyPageReplyData.data.classroomPostListByMyCommentList.map {
                    MyPageReplyData.Data.ClassroomPostListByMyComment(
                        commentCount = it.commentCount,
                        content = it.content,
                        createdAt = it.createdAt,
                        like = MyPageReplyData.Data.ClassroomPostListByMyComment.Like(
                            isLiked = it.like.isLiked,
                            likeCount = it.like.likeCount
                        ),
                        postId = it.postId,
                        title = it.title,
                        writer = MyPageReplyData.Data.ClassroomPostListByMyComment.Writer(
                            nickname = it.writer.nickname,
                            profileImageId = it.writer.profileImageId,
                            writerId = it.writer.writerId
                        )
                    )
                }
            ),
            success = responseMyPageReplyData.success
        )
    }


    //버전 정보
    fun mapperToVersion(responseMyPageVersionData: ResponseMyPageVersionData): MyPageVersionData {
        return MyPageVersionData(
            data = MyPageVersionData.Data(
                AOS = responseMyPageVersionData.data.AOS
            ),
            success = responseMyPageVersionData.success
        )
    }

    //로그아웃
    fun mapperToLogOut(responseMyPageLogOut: ResponseMyPageLogOut): MyPageLogOutData {
        return MyPageLogOutData(
            success = responseMyPageLogOut.success
        )
    }

    //좋아요 목록 조회(리뷰)
    fun mapperToLikeListReview(responseMyPageLikeReview: ResponseMyPageLikeReview): MyPageLikeReviewData {
        return MyPageLikeReviewData(
            data = MyPageLikeReviewData.Data(
                likePostList = responseMyPageLikeReview.data.likePostList.map {
                    MyPageLikeReviewData.Data.LikePost(
                        createdAt = it.createdAt,
                        like = MyPageLikeReviewData.Data.LikePost.Like(
                            isLiked = it.like.isLiked,
                            likeCount = it.like.likeCount
                        ),
                        postId = it.postId,
                        tagList = it.tagList.map { MyPageLikeReviewData.Data.LikePost.Tag(it.tagName) },
                        title = it.title,
                        writer = MyPageLikeReviewData.Data.LikePost.Writer(
                            nickname = it.writer.nickname,
                            writerId = it.writer.writerId
                        )
                    )
                }
            ),
            success = responseMyPageLikeReview.success
        )
    }

    //좋아요 목록 조회(질문)
    fun mapperToLikeListQuestion(responseMyPageLikeQuestion: ResponseMyPageLikeQuestion): MyPageLikeQuestionData {
        return MyPageLikeQuestionData(
            data = MyPageLikeQuestionData.Data(
                likePostList = responseMyPageLikeQuestion.data.likePostList.map {
                    MyPageLikeQuestionData.Data.LikePost(
                        commentCount = it.commentCount,
                        content = it.content,
                        createdAt = it.createdAt,
                        like = MyPageLikeQuestionData.Data.LikePost.Like(
                            isLiked = it.like.isLiked,
                            likeCount = it.like.likeCount
                        ),
                        postId = it.postId,
                        title = it.title,
                        writer = MyPageLikeQuestionData.Data.LikePost.Writer(
                            nickname = it.writer.nickname,
                            writerId = it.writer.writerId
                        )

                    )
                }
            ),
            success = responseMyPageLikeQuestion.success
        )
    }

    //마이페이지 내가 쓴 학과 후기글 조회
    fun mapperToReview(responseMyPageReviewData: ResponseMyPageReviewData): MyPageReviewData {
        return MyPageReviewData(
            data = MyPageReviewData.Data(
                reviewPostList = responseMyPageReviewData.data.reviewPostList.map {
                    MyPageReviewData.Data.ReviewPost(
                        createdAt = it.createdAt,
                        like = MyPageReviewData.Data.ReviewPost.Like(
                            isLiked = it.like.isLiked,
                            likeCount = it.like.likeCount
                        ),
                        majorName = it.majorName,
                        oneLineReview = it.oneLineReview,
                        postId = it.postId,
                        tagList = it.tagList.map { MyPageReviewData.Data.ReviewPost.Tag(it.tagName) }
                    )
                },
                writer = MyPageReviewData.Data.Writer(
                    nickname = responseMyPageReviewData.data.writer.nickname,
                    writerId = responseMyPageReviewData.data.writer.writerId
                )
            ),
            success = responseMyPageReviewData.success
        )
    }

    //마이페이지 차단된 목록 조회
    fun mapperToBlock(responseMyPageBlock: ResponseMyPageBlock): MyPageBlockData {
        return MyPageBlockData(
            data = responseMyPageBlock.data.map {
                MyPageBlockData.Data(
                    nickname = it.nickname,
                    profileImageId = it.profileImageId,
                    userId = it.userId
                )
            },
            success = responseMyPageBlock.success
        )
    }

    //마이페이지 차단 & 차단해제
    fun mapperToBlockUpdateData(responseMyPageBlockUpdate: ResponseMyPageBlockUpdate): MyPageBlockUpdateData {
        return MyPageBlockUpdateData(
            data = MyPageBlockUpdateData.Data(
                blockUserId = responseMyPageBlockUpdate.data.blockUserId,
                blockedUserId = responseMyPageBlockUpdate.data.blockedUserId,
                createdAt = responseMyPageBlockUpdate.data.createdAt,
                id = responseMyPageBlockUpdate.data.id,
                isDeleted = responseMyPageBlockUpdate.data.isDeleted,
                updatedAt = responseMyPageBlockUpdate.data.updatedAt
            ),
            success = responseMyPageBlockUpdate.success
        )
    }

    fun mapperToBlockUpdateItem(myPageBlockUpdateItem: MyPageBlockUpdateItem): RequestMyPageBlockUpdate {
        return RequestMyPageBlockUpdate(
            blockedUserId = myPageBlockUpdateItem.blockedUserId
        )
    }

    //마이페이지 비밀번호 재설정
    fun mapperToResetPasswordData(responseResetPassword: ResponseResetPassword): MyPageResetPasswordData {
        return MyPageResetPasswordData(
            data = responseResetPassword.data,
            status = responseResetPassword.status,
            success = responseResetPassword.success
        )
    }

    fun mapperToResetPasswordItem(myPageResetPasswordItem: MyPageResetPasswordItem): RequestResetPassword {
        return RequestResetPassword(
            email = myPageResetPasswordItem.email
        )
    }

    //각종 링크 조회
    fun mapperToLookUpLinkData(responseMyPageAppLinkData: ResponseMyPageAppLink) : MyPageAppLinkData {
        return MyPageAppLinkData(
            data = MyPageAppLinkData.Data(
                kakaoTalkChannel = responseMyPageAppLinkData.data.kakaoTalkChannel,
                openSourceLicense = responseMyPageAppLinkData.data.openSourceLicense,
                personalInformationPolicy = responseMyPageAppLinkData.data.personalInformationPolicy,
                termsOfService = responseMyPageAppLinkData.data.termsOfService
            ),
            success = responseMyPageAppLinkData.success
        )
    }

}