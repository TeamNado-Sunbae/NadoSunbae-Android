package com.nadosunbae_andorid.presentation.ui.classroom

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nadosunbae_andorid.R
import com.nadosunbae_andorid.data.model.response.classroom.ResponseClassRoomMainData
import com.nadosunbae_andorid.databinding.FragmentQuestionBinding
import com.nadosunbae_andorid.presentation.base.BaseFragment
import com.nadosunbae_andorid.presentation.ui.classroom.adapter.ClassRoomQuestionMainAdapter
import kotlinx.android.synthetic.main.fragment_question.*


class QuestionFragment : BaseFragment<FragmentQuestionBinding>(R.layout.fragment_question) {
    private lateinit var classRoomQuestionMainAdapter : ClassRoomQuestionMainAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        visible()
        initQuestionMain()
    }



    private fun visible(){
        binding.textQuestionAllNoComment.visibility = View.GONE
    }


    private fun initQuestionMain(){
        val exampleData = mutableListOf(
            ResponseClassRoomMainData.Data(
                postId = 32,
                writer = ResponseClassRoomMainData.Data.Writer("호렉",1),
                title = "제목",
                content = "내용",
                createdAt = "2021-11-28T18:56:42.040Z",
                likeCount = 2,
                commentCount = 2
            ),
            ResponseClassRoomMainData.Data(
                postId = 32,
                writer = ResponseClassRoomMainData.Data.Writer("호렉",1),
                title = "제목",
                content = "내용",
                createdAt = "2021-11-28T18:56:42.040Z",
                likeCount = 2,
                commentCount = 2
            ),
                    ResponseClassRoomMainData.Data(
                    postId = 32,
            writer = ResponseClassRoomMainData.Data.Writer("호렉",1),
            title = "제목",
            content = "내용",
            createdAt = "2021-11-28T18:56:42.040Z",
            likeCount = 2,
            commentCount = 2
        ),
            ResponseClassRoomMainData.Data(
                postId = 32,
                writer = ResponseClassRoomMainData.Data.Writer("호렉",1),
                title = "제목",
                content = "내용",
                createdAt = "2021-11-28T18:56:42.040Z",
                likeCount = 2,
                commentCount = 2
            ),
            ResponseClassRoomMainData.Data(
                postId = 32,
                writer = ResponseClassRoomMainData.Data.Writer("호렉",1),
                title = "제목",
                content = "내용",
                createdAt = "2021-11-28T18:56:42.040Z",
                likeCount = 2,
                commentCount = 2
            )
        )


        classRoomQuestionMainAdapter = ClassRoomQuestionMainAdapter()
        binding.rcQuestionAll.adapter = classRoomQuestionMainAdapter
        classRoomQuestionMainAdapter.setQuestionMain(exampleData)
    }
}