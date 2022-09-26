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
import com.nadosunbae_android.app.presentation.ui.classroom.review.ReviewGlobals
import com.nadosunbae_android.app.presentation.ui.main.viewmodel.MainViewModel
import com.nadosunbae_android.domain.model.classroom.ClassRoomData
import com.nadosunbae_android.domain.model.classroom.ClassRoomSeniorData
import com.nadosunbae_android.domain.model.post.PostData
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class ClassRoomQuestionFragment : BaseFragment<FragmentClassRoomQuestionBinding>(R.layout.fragment_class_room_question) {

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
        loadServerData()
    }

    private fun initBinding() {
        binding.mainViewModel = mainViewModel
    }

    private fun initAdapter() {
        // 우리과 선배 목록
        classRoomSeniorAdapter = ClassRoomSeniorOnAdapter(link)
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

    private fun setListener() {
        binding.btnClassroomMoreSenior.setOnClickListener {
            mainViewModel.classRoomFragmentNum.postValue(3)
        }
    }

    private fun loadServerData(){
//        mainViewModel.selectedMajor.observe(viewLifecycleOwner){
//            showLoading()
//            mainViewModel.getClassRoomMain(2,it.majorId, "recent")
//        }
        classRoomQuestionViewModel.onLoadingEnd.value = false
        classRoomQuestionViewModel.getSeniorList(ReviewGlobals.selectedMajor!!.majorId, null)
        classRoomQuestionViewModel.getQuestionList(mainViewModel.univId.value!!, ReviewGlobals.selectedMajor!!.majorId)
    }

    //로딩 종료
    private fun observeLoadingEnd() {
        mainViewModel.onLoadingEnd.observe(viewLifecycleOwner) {
            dismissLoading()
        }
    }

    private fun observeData() {
        classRoomQuestionViewModel.seniorList.observe(requireActivity()) {
            classRoomSeniorAdapter.setOnQuestionUser(
                classRoomQuestionViewModel.seniorList.value?.onQuestionUserList as MutableList<ClassRoomSeniorData.UserSummaryData>
            )
        }
        classRoomQuestionViewModel.questionList.observe(requireActivity()) {
            if (classRoomQuestionViewModel.questionList.value != null) {
                classRoomInfoMainAdapter.setQuestionMain(
                    mapToPostData(classRoomQuestionViewModel.questionList.value!!) as MutableList<ClassRoomData>
                )
            }
        }
    }

    //선배 Id = userId가 같을 경우 마이페이지로 이동
    private fun goMyPage(seniorId : Int){
        val userId = mainViewModel.userId.value ?: 0
        Timber.d("userId : $userId")
        Timber.d("seniorId : $seniorId")
        if(userId == seniorId){
            mainViewModel.bottomNavItem.value = 4
        }else{
            mainViewModel.classRoomFragmentNum.value = 4
            mainViewModel.initLoading.value = true
        }
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