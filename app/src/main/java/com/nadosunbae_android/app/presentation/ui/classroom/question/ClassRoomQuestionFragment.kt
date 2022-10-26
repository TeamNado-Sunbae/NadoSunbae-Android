package com.nadosunbae_android.app.presentation.ui.classroom.question

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.databinding.FragmentClassRoomQuestionBinding
import com.nadosunbae_android.app.presentation.base.BaseFragment
import com.nadosunbae_android.app.presentation.ui.classroom.adapter.ClassRoomInfoMainAdapter
import com.nadosunbae_android.app.presentation.ui.classroom.adapter.ClassRoomSeniorOnAdapter
import com.nadosunbae_android.app.presentation.ui.classroom.question.viewmodel.ClassRoomQuestionViewModel
import com.nadosunbae_android.app.presentation.ui.main.viewmodel.MainViewModel
import com.nadosunbae_android.domain.model.classroom.ClassRoomData
import com.nadosunbae_android.domain.model.classroom.ClassRoomSeniorData
import com.nadosunbae_android.domain.model.post.PostData
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class ClassRoomQuestionFragment :
    BaseFragment<FragmentClassRoomQuestionBinding>(R.layout.fragment_class_room_question) {

    // main view model 초기화
    private val mainViewModel: MainViewModel by activityViewModels()
    private val classRoomQuestionViewModel: ClassRoomQuestionViewModel by viewModels()

    private lateinit var classRoomInfoMainAdapter: ClassRoomInfoMainAdapter
    private lateinit var classRoomSeniorAdapter: ClassRoomSeniorOnAdapter

    var link = QuestionDataToFragment()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initBinding()
        initAdapter()
        setListener()
        observeLoadingEnd()
        observeData()
        observeSelectedMajor()
        setOnClickListener()
    }

    private fun initBinding() {
        binding.mainViewModel = mainViewModel
    }

    private fun initAdapter() {
        // 우리과 선배 목록
        classRoomSeniorAdapter = ClassRoomSeniorOnAdapter(link, true)
        binding.rvClassroomRecommendSenior.adapter = classRoomSeniorAdapter


//        mainViewModel.seniorData.observe(viewLifecycleOwner) {
//            val userList = it.onQuestionUserList as MutableList<ClassRoomSeniorData.OnQuestionUser>
//            classRoomSeniorAdapter.setOnQuestionUser(userList)
//            Timber.d("asdf ${userList}")
//
//            if (userList.isNullOrEmpty())
//                binding.tvClassroomRecommendSeniorEmpty.visibility = View.VISIBLE
//            else
//                binding.tvClassroomRecommendSeniorEmpty.visibility = View.GONE
//        }

        // 1:1 질문
        val userId = mainViewModel.userId.value ?: 0
        classRoomInfoMainAdapter = ClassRoomInfoMainAdapter(userId)
        binding.rvSeniorPersonal.adapter = classRoomInfoMainAdapter

//        mainViewModel.classRoomMain.observe(viewLifecycleOwner){
//            Timber.d("classRoomInfo: $it")
//            if(it.isEmpty()){
//                mainViewModel.classRoomInfoEmpty.value = InformationFragment.EMPTY
//                binding.tvClassroomQuestionEmpty.visibility = View.VISIBLE
//            }else{
//                mainViewModel.classRoomInfoEmpty.value = InformationFragment.NOTEMPTY
//                binding.tvClassroomQuestionEmpty.visibility = View.GONE
//            }
//            Timber.d("classRoomInfo empty : ${mainViewModel.classRoomInfoEmpty.value}")
//            classRoomInfoMainAdapter.setQuestionMain(it as MutableList<ClassRoomData>)
//        }
    }


    //선배 없을 때
    private fun initSeniorEmpty(size: Int?) {
        if (size == 0 || size == null) {
            binding.tvClassroomRecommendSeniorEmpty.visibility = View.VISIBLE
            binding.btnClassroomMoreSenior.visibility = View.INVISIBLE
        } else {
            binding.tvClassroomRecommendSeniorEmpty.visibility = View.INVISIBLE
            binding.btnClassroomMoreSenior.visibility = View.VISIBLE
        }
    }

    //질문 없을 때
    private fun initQuestionEmpty(size: Int?) {
        if (size == 0 || size == null) {
            binding.tvClassroomQuestionEmpty.visibility = View.VISIBLE
        } else {
            binding.tvClassroomQuestionEmpty.visibility = View.GONE
        }
    }

    private fun setListener() {
        binding.btnClassroomMoreSenior.setOnClickListener {
            mainViewModel.classRoomFragmentNum.postValue(3)
        }
    }

    private fun observeSelectedMajor() {
        mainViewModel.selectedMajor.observe(requireActivity()) {
            classRoomQuestionViewModel.getSeniorList(it.majorId, null)
            classRoomQuestionViewModel.getQuestionList(mainViewModel.univId.value!!, it.majorId)
        }
    }

    //리사이클러뷰에 있는 더보기 버튼 눌렀을 때 이동
    private fun setOnClickListener() {
        classRoomSeniorAdapter.setItemClickListener(
            object : ClassRoomSeniorOnAdapter.ItemClickListener {
                override fun onClick(view: View) {
                    mainViewModel.classRoomFragmentNum.postValue(3)
                }

            }
        )
    }

    //로딩 종료
    private fun observeLoadingEnd() {
        classRoomQuestionViewModel.onLoadingEnd.observe(viewLifecycleOwner) {
            if (it == true)
                dismissLoading()
        }
    }

    private fun observeData() {
        classRoomQuestionViewModel.seniorList.observe(requireActivity()) {

            var allSeniorList = mutableListOf<ClassRoomSeniorData.UserSummaryData>()
            allSeniorList.apply {
                addAll(it.onQuestionUserList)
                addAll(it.offQuestionUserList)
                sortBy { item -> item.rate }
                if (this.size > 8)
                    allSeniorList = this.subList(0, 8)
            }
            initSeniorEmpty(allSeniorList.size)
            classRoomSeniorAdapter.setOnQuestionUser(allSeniorList)

        }

        classRoomQuestionViewModel.questionList.observe(requireActivity()) {
            initQuestionEmpty(it.size)
            classRoomInfoMainAdapter.setQuestionMain(
                mapToPostData(classRoomQuestionViewModel.questionList.value!!) as MutableList<ClassRoomData>
            )


        }
    }

    //선배 Id = userId가 같을 경우 마이페이지로 이동
    private fun goMyPage(seniorId: Int) {
        val userId = mainViewModel.userId.value ?: 0
        Timber.d("userId : $userId")
        Timber.d("seniorId : $seniorId")
        if (userId == seniorId) {
            mainViewModel.bottomNavItem.value = 4
        } else {
            mainViewModel.classRoomFragmentNum.value = 4
            mainViewModel.initLoading.value = true
        }
        mainViewModel.seniorBack.value = 3
    }

    private fun mapToPostData(postData: List<PostData>): List<ClassRoomData> =
        postData.map {
            ClassRoomData(
                postId = it.postId,
                title = it.title,
                content = it.content,
                createdAt = it.createdAt,
                writer = ClassRoomData.Writer(
                    nickname = it.nickname,
                    writerId = it.id,
                    profileImageId = 4
                ),
                likeCount = it.likeCount,
                isLiked = it.isLiked,
                commentCount = it.commentCount
            )
        }

    inner class QuestionDataToFragment : DataToFragment {
        override fun getSeniorId(seniorId: Int) {
            mainViewModel.seniorId.value = seniorId
            goMyPage(seniorId)
        }
    }

}