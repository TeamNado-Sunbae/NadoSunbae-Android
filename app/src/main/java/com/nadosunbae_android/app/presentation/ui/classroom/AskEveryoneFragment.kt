package com.nadosunbae_android.app.presentation.ui.classroom

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.databinding.FragmentAskEveryoneBinding
import com.nadosunbae_android.domain.model.classroom.ClassRoomData
import com.nadosunbae_android.app.presentation.base.BaseFragment
import com.nadosunbae_android.app.presentation.ui.classroom.adapter.ClassRoomAskEveryoneAdapter
import com.nadosunbae_android.app.presentation.ui.classroom.viewmodel.AskEveryOneViewModel
import com.nadosunbae_android.app.presentation.ui.main.MainGlobals
import com.nadosunbae_android.app.presentation.ui.main.viewmodel.MainViewModel
import com.nadosunbae_android.app.presentation.ui.review.ReviewGlobals
import com.nadosunbae_android.app.util.CustomDialog
import com.nadosunbae_android.app.util.dpToPx
import com.nadosunbae_android.app.util.showCustomDropDown
import com.nadosunbae_android.domain.model.main.SelectableData
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AskEveryoneFragment : BaseFragment<FragmentAskEveryoneBinding>(R.layout.fragment_ask_everyone) {
    private val mainViewModel: MainViewModel by activityViewModels()
    private val askEveryOneViewModel : AskEveryOneViewModel by viewModels()
    private lateinit var classRoomAskEveryoneAdapter : ClassRoomAskEveryoneAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        changeClassRoom()
        initQuestionSort()
        observeArray()
        questionSort()
        observeLoadingEnd()
        initAskEveryone()
        goQuestionWrite()




    }

    //로딩 종료
    private fun observeLoadingEnd() {
        mainViewModel.onLoadingEnd.observe(viewLifecycleOwner){
            dismissLoading()
        }
    }

    //과방탭 메인으로 이동
    private fun changeClassRoom(){
        binding.imgAskEveroneTitle.setOnClickListener {
            mainViewModel.classRoomFragmentNum.value = 1
        }
        binding.textAskEveryoneTitle.setOnClickListener {
            mainViewModel.classRoomFragmentNum.value = 1
        }
    }

    //리사이클러뷰
    private fun initAskEveryone(){
        //majorId 넣음
        classRoomAskEveryoneAdapter = ClassRoomAskEveryoneAdapter(mainViewModel.userId.value ?: 0)
        binding.rcAskEveryone.adapter = classRoomAskEveryoneAdapter
        mainViewModel.classRoomMain.observe(viewLifecycleOwner){
            classRoomAskEveryoneAdapter.setAskEveryone(it as MutableList<ClassRoomData>)
        }
    }

    //질문 전체보기 서버통신
    private fun questionEveryone(sort : String){
        mainViewModel.selectedMajor.observe(viewLifecycleOwner){
            showLoading()
            mainViewModel.getClassRoomMain(3,it.majorId,sort)
        }

    }


    override fun onResume() {
        super.onResume()
        questionEveryone("recent")
    }

    //전체 질문 작성으로 이동
    private fun goQuestionWrite() {
        binding.btnGoQuestionWrite.setOnClickListener {
            CustomDialog(requireActivity()).restrictDialog(
                requireActivity(),
                ReviewGlobals.isReviewed,
                MainGlobals.signInData!!.isUserReported,
                MainGlobals.signInData!!.isReviewInappropriate,
                MainGlobals.signInData?.message.toString(),
                behavior = {
                    val intent = Intent(requireActivity(), QuestionWriteActivity::class.java)
                    intent.apply {
                        putExtra("division", 0)
                        putExtra("title", "전체에게 질문 작성")
                        putExtra("postTypeId", 3)
                        putExtra("majorId", mainViewModel.selectedMajor.value?.majorId)
                        putExtra("hintContent", getString(R.string.classroom_question_write_hint))
                    }
                    startActivity(intent)
                }
            )
        }
    }

    //최신순, 도움순 정렬
    private fun questionSort(){
        binding.btnAskEveryoneArray.setOnClickListener {
            val questionDropDownList = mutableListOf<SelectableData>(
            SelectableData(1, getString(R.string.review_latest_order), true),
            SelectableData(2, getString(R.string.review_likes_order), false)
        )


            showCustomDropDown(askEveryOneViewModel, binding.btnAskEveryoneArray, 160f.dpToPx, null, -1 * 16f.dpToPx, null, true,askEveryOneViewModel.dropDownSelected.value!!.id, questionDropDownList)

        }
    }

    //첫 화면 최신순
    private fun initQuestionSort(){
        askEveryOneViewModel.dropDownSelected.value = SelectableData(1,"최신순",true)
    }

    //최신순, 도움순 변경
    private fun observeArray(){
        askEveryOneViewModel.dropDownSelected.observe(viewLifecycleOwner) {
            val sortData = askEveryOneViewModel.dropDownSelected.value
            if (sortData != null) {
                if (sortData.id == 1)
                    binding.btnAskEveryoneArray.text = getString(R.string.review_latest_order)
                else
                    binding.btnAskEveryoneArray.text = getString(R.string.review_likes_order)
            }
            var sort = "recent"
            if (askEveryOneViewModel.dropDownSelected.value != null) {
                sort = if (askEveryOneViewModel.dropDownSelected.value!!.id == 1)
                    "recent"
                else
                    "like"
            }

            questionEveryone(sort)
        }

    }


}