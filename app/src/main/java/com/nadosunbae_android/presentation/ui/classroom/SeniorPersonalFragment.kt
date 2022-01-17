package com.nadosunbae_android.presentation.ui.classroom

import android.os.Bundle
import android.view.View
import com.nadosunbae_android.R
import com.nadosunbae_android.data.model.response.classroom.ResponseClassRoomMainData
import com.nadosunbae_android.databinding.FragmentSeniorBinding
import com.nadosunbae_android.databinding.FragmentSeniorPersonalBinding
import com.nadosunbae_android.presentation.base.BaseFragment
import com.nadosunbae_android.presentation.ui.classroom.adapter.ClassRoomQuestionMainAdapter

class SeniorPersonalFragment : BaseFragment<FragmentSeniorPersonalBinding>(R.layout.fragment_senior_personal) {
    private lateinit var classRoomQuestionMainAdapter : ClassRoomQuestionMainAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSeniorQuestion()
    }

    //선배에게 온 1:1 질문 목록
    private fun initSeniorQuestion(){
        val exampleData = mutableListOf(
            ResponseClassRoomMainData.Data(
                postId = 32,
                writer = ResponseClassRoomMainData.Data.Writer("호렉",1,1),
                title = "제목",
                content = "내용",
                createdAt = "2021-11-28T18:56:42.040Z",
                likeCount = 2,
                commentCount = 2
            ),
            ResponseClassRoomMainData.Data(
                postId = 32,
                writer = ResponseClassRoomMainData.Data.Writer("호렉",1,1),
                title = "제목",
                content = "내용",
                createdAt = "2021-11-28T18:56:42.040Z",
                likeCount = 2,
                commentCount = 2
            ),
            ResponseClassRoomMainData.Data(
                postId = 32,
                writer = ResponseClassRoomMainData.Data.Writer("호렉",1,1),
                title = "제목",
                content = "내용",
                createdAt = "2021-11-28T18:56:42.040Z",
                likeCount = 2,
                commentCount = 2
            ),
            ResponseClassRoomMainData.Data(
                postId = 32,
                writer = ResponseClassRoomMainData.Data.Writer("호렉",1,1),
                title = "제목",
                content = "내용",
                createdAt = "2021-11-28T18:56:42.040Z",
                likeCount = 2,
                commentCount = 2
            ),
            ResponseClassRoomMainData.Data(
                postId = 32,
                writer = ResponseClassRoomMainData.Data.Writer("호렉",1,1),
                title = "제목",
                content = "내용",
                createdAt = "2021-11-28T18:56:42.040Z",
                likeCount = 2,
                commentCount = 2
            ), ResponseClassRoomMainData.Data(
                postId = 32,
                writer = ResponseClassRoomMainData.Data.Writer("호렉",1,1),
                title = "제목",
                content = "내용",
                createdAt = "2021-11-28T18:56:42.040Z",
                likeCount = 2,
                commentCount = 2
            ), ResponseClassRoomMainData.Data(
                postId = 32,
                writer = ResponseClassRoomMainData.Data.Writer("호렉",1,1),
                title = "제목",
                content = "내용",
                createdAt = "2021-11-28T18:56:42.040Z",
                likeCount = 2,
                commentCount = 2
            ),
        )

        classRoomQuestionMainAdapter = ClassRoomQuestionMainAdapter()
        binding.rcSeniorPersonal.adapter = classRoomQuestionMainAdapter
        classRoomQuestionMainAdapter.setQuestionMain(exampleData)

    }

}