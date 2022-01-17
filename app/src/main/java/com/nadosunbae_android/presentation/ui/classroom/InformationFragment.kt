package com.nadosunbae_android.presentation.ui.classroom

import android.os.Bundle
import android.view.View
import com.nadosunbae_android.R
import com.nadosunbae_android.data.model.response.classroom.ResponseClassRoomMainData
import com.nadosunbae_android.databinding.FragmentInformationBinding
import com.nadosunbae_android.presentation.base.BaseFragment
import com.nadosunbae_android.presentation.ui.classroom.adapter.ClassRoomInfoDetailAdapter
import com.nadosunbae_android.presentation.ui.classroom.adapter.ClassRoomQuestionMainAdapter


class InformationFragment : BaseFragment<FragmentInformationBinding>(R.layout.fragment_information) {
    private lateinit var classRoomInfoMainAdapter : ClassRoomInfoDetailAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initInfoMain()
    }


    private fun initInfoMain(){
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
        classRoomInfoMainAdapter = ClassRoomInfoDetailAdapter()
        binding.rcClassroomInfo.adapter = classRoomInfoMainAdapter
        classRoomInfoMainAdapter.setQuestionMain(exampleData)

    }
}