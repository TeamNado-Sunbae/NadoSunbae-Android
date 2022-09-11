package com.nadosunbae_android.data.mapper.mypage

import com.nadosunbae_android.data.model.request.mypage.RequestMyPageBlockUpdate
import com.nadosunbae_android.data.model.request.mypage.RequestMyPageModify
import com.nadosunbae_android.data.model.request.mypage.RequestQuit
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
                bio = responseMypageMyInfo.data.bio,
                count = responseMypageMyInfo.data.count,
                firstMajorName = responseMypageMyInfo.data.firstMajorName,
                firstMajorStart = responseMypageMyInfo.data.firstMajorStart,
                isOnQuestion = responseMypageMyInfo.data.isOnQuestion,
                nickname = responseMypageMyInfo.data.nickname,
                profileImageId = responseMypageMyInfo.data.profileImageId,
                responseRate = responseMypageMyInfo.data.responseRate,
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
                bio = responseMyPageModify.data.bio,
                firstMajorId = responseMyPageModify.data.firstMajorId,
                firstMajorStart = responseMyPageModify.data.firstMajorStart,
                isOnQuestion = responseMyPageModify.data.isOnQuestion,
                nickname = responseMyPageModify.data.nickname,
                profileImageId = responseMyPageModify.data.profileImageId,
                secondMajorId = responseMyPageModify.data.secondMajorId,
                secondMajorStart = responseMyPageModify.data.secondMajorStart,
            ),
            success = responseMyPageModify.success
        )
    }

    fun mapperToModifyItem(myPageModifyItem: MyPageModifyItem): RequestMyPageModify {
        return RequestMyPageModify(
            profileImageId = myPageModifyItem.profileImageId,
            nickname = myPageModifyItem.nickname,
            bio = myPageModifyItem.bio,
            firstMajorId = myPageModifyItem.firstMajorId,
            firstMajorStart = myPageModifyItem.firstMajorStart,
            secondMajorId = myPageModifyItem.secondMajorId,
            secondMajorStart = myPageModifyItem.secondMajorStart,
            isOnQuestion = myPageModifyItem.isOnQuestion
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
                        postTypeId = it.postTypeId,
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
                        postTypeId = it.postTypeId,
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

    //마이페이지 회원탈퇴
    fun mapperToQuitData(responseQuitData: ResponseQuitData): MyPageQuitData {
        return MyPageQuitData(
            data = MyPageQuitData.Data(
                comment = responseQuitData.data.comment.map {
                    MyPageQuitData.Data.Comments(
                        id = it.id,
                        isDeleted = it.isDeleted,
                        updatedAt = it.updatedAt
                    )
                },
                block = responseQuitData.data.block.map {
                    MyPageQuitData.Data.Block(
                        id = it.id,
                        isDeleted = it.isDeleted,
                        updatedAt = it.updatedAt
                    )
                },
                classroomPost = responseQuitData.data.classroomPost.map{
                    MyPageQuitData.Data.ClassroomPost(
                        id = it.id,
                        isDeleted = it.isDeleted,
                        updatedAt = it.updatedAt
                    )
                },
                like = responseQuitData.data.like.map{
                    MyPageQuitData.Data.Like(
                        id = it.id,
                        isLiked = it.isLiked,
                        updatedAt = it.updatedAt
                    )
                },
                notification = responseQuitData.data.notification.map {
                    MyPageQuitData.Data.Notification(
                        id = it.id,
                        isDeleted = it.isDeleted,
                        updatedAt = it.updatedAt
                    )
                },
                report = responseQuitData.data.report.map{
                    MyPageQuitData.Data.Report(
                        id = it.id,
                        isDeleted = it.isDeleted,
                        updatedAt = it.updatedAt
                    )
                },
                reviewPost = responseQuitData.data.reviewPost.map {
                    MyPageQuitData.Data.ReviewPost(
                        id = it.id,
                        isDeleted = it.isDeleted,
                        updatedAt = it.updatedAt
                    )
                },
                user = MyPageQuitData.Data.User(
                    email = responseQuitData.data.user.email,
                    id = responseQuitData.data.user.id,
                    isDeleted = responseQuitData.data.user.isDeleted,
                    updatedAt = responseQuitData.data.user.updatedAt
                )
            ),
            status = responseQuitData.status,
            success = responseQuitData.success
        )
    }


    fun mapperToQuitItem(myPageQuitItem: MyPageQuitItem) : RequestQuit {
        return RequestQuit(
            password = myPageQuitItem.password
        )
    }
}