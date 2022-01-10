package com.nadosunbae_andorid.presentation.ui.classroom

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nadosunbae_andorid.R
import com.nadosunbae_andorid.data.model.response.classroom.ResponseClassRoomMainData
import com.nadosunbae_andorid.databinding.FragmentAskEveryoneBinding
import com.nadosunbae_andorid.presentation.base.BaseFragment
import com.nadosunbae_andorid.presentation.ui.classroom.adapter.ClassRoomAskEveryoneAdapter
import com.nadosunbae_andorid.presentation.ui.main.viewmodel.MainViewModel


class AskEveryoneFragment : BaseFragment<FragmentAskEveryoneBinding>(R.layout.fragment_ask_everyone) {
    private val mainViewModel: MainViewModel by activityViewModels{
        object : ViewModelProvider.Factory{
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return MainViewModel() as T
            }
        }
    }
    private lateinit var classRoomAskEveryoneAdapter : ClassRoomAskEveryoneAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        changeClassRoom()
        initAskEveryone()
    }


    //과방탭 메인으로 이동
    private fun changeClassRoom(){
        binding.imgAskEveroneTitle.setOnClickListener {
            mainViewModel.classRoomFragmentNum.value = 1
        }
    }

    //리사이클러뷰
    private fun initAskEveryone(){
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
            ), ResponseClassRoomMainData.Data(
                postId = 32,
                writer = ResponseClassRoomMainData.Data.Writer("호렉",1),
                title = "제목",
                content = "내용",
                createdAt = "2021-11-28T18:56:42.040Z",
                likeCount = 2,
                commentCount = 2
            ), ResponseClassRoomMainData.Data(
                postId = 32,
                writer = ResponseClassRoomMainData.Data.Writer("호렉",1),
                title = "제목",
                content = "내용",
                createdAt = "2021-11-28T18:56:42.040Z",
                likeCount = 2,
                commentCount = 2
            )
        )
        classRoomAskEveryoneAdapter = ClassRoomAskEveryoneAdapter()
        binding.rcAskEveryone.adapter = classRoomAskEveryoneAdapter
        classRoomAskEveryoneAdapter.setAskEveryone(exampleData)

    }


}