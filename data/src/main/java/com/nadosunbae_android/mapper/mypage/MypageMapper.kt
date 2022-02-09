package com.nadosunbae_android.mapper.mypage

import androidx.lifecycle.Transformations.map
import com.nadosunbae_android.model.mypage.MyPageMyInfo
import com.nadosunbae_android.model.mypage.MyPageQuestionData
import com.nadosunbae_android.model.response.mypage.ResponseMypageMyInfo
import com.nadosunbae_android.model.response.mypage.ResponseMypageQuestionData

object MypageMapper {
    fun mapperToQuestion(responseMypageQuestionData: ResponseMypageQuestionData): MyPageQuestionData {
        return MyPageQuestionData(
            data = MyPageQuestionData.Data(
                classroomPostList = responseMypageQuestionData.data.classroomPostList.map {
                    MyPageQuestionData.Data.ClassroomPost(
                        commentCount = it.commentCount,
                        content = it.content,
                        createdAt = it.createdAt,
                        likeCount = it.likeCount,
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

    fun mapperToMyInfo(responseMypageMyInfo: ResponseMypageMyInfo) : MyPageMyInfo {
        return MyPageMyInfo(
            data = MyPageMyInfo.Data(
                firstMajorName = responseMypageMyInfo.data.firstMajorName,
                firstMajorStart = responseMypageMyInfo.data.firstMajorStart,
                isOnQuestion = responseMypageMyInfo.data.isOnQuestion,
                likeCount = responseMypageMyInfo.data.likeCount,
                nickname = responseMypageMyInfo.data.nickname,
                profileImageId = responseMypageMyInfo.data.profileImageId,
                secondMajorName = responseMypageMyInfo.data.secondMajorName,
                secondMajorStart = responseMypageMyInfo.data.secondMajorStart,
                userId = responseMypageMyInfo.data.userId
            ),
            success = responseMypageMyInfo.success
        )
    }
}