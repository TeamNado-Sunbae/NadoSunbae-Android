package com.nadosunbae_android.presentation.ui.mypage

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nadosunbae_android.R
import com.nadosunbae_android.data.model.response.classroom.ResponseClassRoomMainData
import com.nadosunbae_android.data.model.response.mypage.ResponseMypageQuestionData
import com.nadosunbae_android.data.model.ui.MyPageData
import com.nadosunbae_android.data.model.ui.classroom.ClassRoomData
import com.nadosunbae_android.databinding.FragmentMyPageBinding
import com.nadosunbae_android.presentation.base.BaseFragment
import com.nadosunbae_android.presentation.ui.classroom.adapter.ClassRoomAskEveryoneAdapter
import com.nadosunbae_android.presentation.ui.classroom.adapter.ClassRoomQuestionMainAdapter
import com.nadosunbae_android.presentation.ui.main.viewmodel.MainViewModel
import com.nadosunbae_android.presentation.ui.mypage.viewmodel.MyPageViewModel
import com.nadosunbae_android.presentation.ui.review.adapter.ReviewListAdapter
import com.nadosunbae_android.util.Mapper


class MyPageFragment : BaseFragment<FragmentMyPageBinding>(R.layout.fragment_my_page) {

    private val myPageViewModel: MyPageViewModel by viewModels{
        object : ViewModelProvider.Factory{
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return MyPageViewModel() as T
            }
        }
    }

    private val mainViewModel: MainViewModel by activityViewModels{
        object : ViewModelProvider.Factory{
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return MainViewModel() as T
            }
        }
    }



    private lateinit var myPageQuestionAdapter : ClassRoomQuestionMainAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAskPersonal()
        movePage()
    }


    private fun movePage() {
        binding.textMyPagePostByMe.setOnClickListener {
            val intentMyPagePost = Intent(getActivity(), MyPagePostActivity::class.java)
            startActivity(intentMyPagePost)
        }

        binding.textMyPageReplyByMe.setOnClickListener {
            val intentMyPageReply = Intent(getActivity(), MyPageReplyActivity::class.java)
            startActivity(intentMyPageReply)
        }

        binding.textMyPageReview.setOnClickListener {
            val intentMyPageReview = Intent(getActivity(), MyPageClassroomReviewActivity::class.java)
            startActivity(intentMyPageReview)
        }

        binding.clMyPageHeartList.setOnClickListener {
            val intentHeartList = Intent(getActivity(), MyPageLikeListActivity::class.java)
            startActivity(intentHeartList)
        }
    }



    private fun initAskPersonal() {
        myPageViewModel.getMyPageQuestion(49, "recent")

        myPageQuestionAdapter = ClassRoomQuestionMainAdapter(2)
        binding.rcMyPageQuestion.adapter = myPageQuestionAdapter
        //classRoomAskEveryoneAdapter.setAskEveryone()

        myPageViewModel.personalQuestion.observe(viewLifecycleOwner){
            myPageQuestionAdapter.setQuestionMain(Mapper.mapperToMyPageQuestion(it) as MutableList<ClassRoomData>)
        }

        }


        /*
        myPageViewModel.personalQuestion.observe(viewLifecycleOwner) {
            classRoomAskEveryoneAdapter.setAskEveryone(Mapper.mapperToQuestionMain() as MutableList<ClassRoomData>)
        }*/
    }

    //리사이클러뷰
    /*private fun initAskPersonal(){
        val exampleData = mutableListOf(
            ResponseClassRoomMainData.Data(
                postId = 32,
                writer = ResponseClassRoomMainData.Data.Writer("호렉",1,1),
                title = "제목",
                content = "내용",
                createdAt = "2021-11-28T18:56:42.040Z",
                likeCount = "2",
                commentCount = "2"
            ),
            ResponseClassRoomMainData.Data(
                postId = 32,
                writer = ResponseClassRoomMainData.Data.Writer("호렉",1,1),
                title = "제목",
                content = "내용",
                createdAt = "2021-11-28T18:56:42.040Z",
                likeCount = "2",
                commentCount = "2"
            ),
            ResponseClassRoomMainData.Data(
                postId = 32,
                writer = ResponseClassRoomMainData.Data.Writer("호렉",1,1),
                title = "제목",
                content = "내용",
                createdAt = "2021-11-28T18:56:42.040Z",
                likeCount = "2",
                commentCount = "2"
            ),
            ResponseClassRoomMainData.Data(
                postId = 32,
                writer = ResponseClassRoomMainData.Data.Writer("호렉",1,1),
                title = "제목",
                content = "내용",
                createdAt = "2021-11-28T18:56:42.040Z",
                likeCount = "2",
                commentCount = "2"
            ),
            ResponseClassRoomMainData.Data(
                postId = 32,
                writer = ResponseClassRoomMainData.Data.Writer("호렉",1,1),
                title = "제목",
                content = "내용",
                createdAt = "2021-11-28T18:56:42.040Z",
                likeCount = "2",
                commentCount = "2"
            ), ResponseClassRoomMainData.Data(
                postId = 32,
                writer = ResponseClassRoomMainData.Data.Writer("호렉",1,1),
                title = "제목",
                content = "내용",
                createdAt = "2021-11-28T18:56:42.040Z",
                likeCount = "2",
                commentCount = "2"
            ), ResponseClassRoomMainData.Data(
                postId = 32,
                writer = ResponseClassRoomMainData.Data.Writer("호렉",1,1),
                title = "제목",
                content = "내용",
                createdAt = "2021-11-28T18:56:42.040Z",
                likeCount = "2",
                commentCount = "2"
            ),
        )
        classRoomAskEveryoneAdapter = ClassRoomAskEveryoneAdapter()
        binding.rcMyPageQuestion.adapter = classRoomAskEveryoneAdapter
        classRoomAskEveryoneAdapter.setAskEveryone(exampleData)
    } */





