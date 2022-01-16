package com.nadosunbae_andorid.presentation.ui.classroom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nadosunbae_andorid.R
import com.nadosunbae_andorid.data.model.response.classroom.ResponseClassRoomQuestionDetail
import com.nadosunbae_andorid.databinding.ActivityQuestionDetailBinding
import com.nadosunbae_andorid.presentation.base.BaseActivity
import com.nadosunbae_andorid.presentation.ui.classroom.adapter.ClassRoomQuestionDetailAdapter

class QuestionDetailActivity :
    BaseActivity<ActivityQuestionDetailBinding>(R.layout.activity_question_detail) {
    private lateinit var classRoomQuestionDetailAdapter: ClassRoomQuestionDetailAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initQuestionDetail()
    }


    // 1:1 질문 상세보기
    private fun initQuestionDetail() {
        val example = mutableListOf(
                    ResponseClassRoomQuestionDetail.Data.Message(
                        messageId = 1,
                        title = "제목입니다",
                        content = "내용입니다",
                        createdAt = "2021-11-28T18:56:42.040Z",
                        isDeleted = false,
                        writer = ResponseClassRoomQuestionDetail.Data.Message.Writer(
                            writerId = 1,
                            profileImageId = 2,
                            isQuestioner = true,
                            nickname = "글을 쓴 사람 닉네임",
                            firstMajorName = "경영학과",
                            firstMajorStart = "18-1",
                            secondMajorName = "미진입",
                            secondMajorStart = ""
                        )
                    ),
                    ResponseClassRoomQuestionDetail.Data.Message(
                        messageId = 1,
                        title = "제목입니다",
                        content = "내용입니다",
                        createdAt = "2021-11-28T18:56:42.040Z",
                        isDeleted = false,
                        writer = ResponseClassRoomQuestionDetail.Data.Message.Writer(
                            writerId = 1,
                            profileImageId = 2,
                            isQuestioner = false,
                            nickname = "글을 쓴 사람 닉네임",
                            firstMajorName = "경영학과",
                            firstMajorStart = "18-1",
                            secondMajorName = "미진입",
                            secondMajorStart = ""
                        )
                    ),
            ResponseClassRoomQuestionDetail.Data.Message(
                messageId = 1,
                title = "제목입니다",
                content = "내용입니다",
                createdAt = "2021-11-28T18:56:42.040Z",
                isDeleted = false,
                writer = ResponseClassRoomQuestionDetail.Data.Message.Writer(
                    writerId = 1,
                    profileImageId = 2,
                    isQuestioner = false,
                    nickname = "글을 쓴 사람 닉네임",
                    firstMajorName = "경영학과",
                    firstMajorStart = "18-1",
                    secondMajorName = "미진입",
                    secondMajorStart = ""
                )
            ),
            ResponseClassRoomQuestionDetail.Data.Message(
                messageId = 1,
                title = "제목입니다",
                content = "내용입니다",
                createdAt = "2021-11-28T18:56:42.040Z",
                isDeleted = false,
                writer = ResponseClassRoomQuestionDetail.Data.Message.Writer(
                    writerId = 1,
                    profileImageId = 2,
                    isQuestioner = true,
                    nickname = "글을 쓴 사람 닉네임",
                    firstMajorName = "경영학과",
                    firstMajorStart = "18-1",
                    secondMajorName = "미진입",
                    secondMajorStart = ""
                )
            )
                )



        classRoomQuestionDetailAdapter = ClassRoomQuestionDetailAdapter(this)
        binding.rcQuestionDetail.adapter = classRoomQuestionDetailAdapter
        classRoomQuestionDetailAdapter.setQuestionDetail(example)

    }
}