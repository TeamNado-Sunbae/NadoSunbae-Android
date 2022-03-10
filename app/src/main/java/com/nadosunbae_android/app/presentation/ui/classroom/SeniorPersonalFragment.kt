package com.nadosunbae_android.app.presentation.ui.classroom

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.databinding.FragmentSeniorPersonalBinding
import com.nadosunbae_android.app.presentation.base.BaseFragment
import com.nadosunbae_android.app.presentation.ui.classroom.adapter.ClassRoomQuestionMainAdapter
import com.nadosunbae_android.app.presentation.ui.classroom.viewmodel.SeniorPersonalViewModel
import com.nadosunbae_android.app.presentation.ui.main.MainGlobals
import com.nadosunbae_android.app.presentation.ui.main.viewmodel.MainViewModel
import com.nadosunbae_android.app.presentation.ui.mypage.MyPageClassroomReviewActivity
import com.nadosunbae_android.app.presentation.ui.review.ReviewGlobals
import com.nadosunbae_android.app.util.CustomDialog
import com.nadosunbae_android.app.util.dpToPx
import com.nadosunbae_android.app.util.showCustomDropDown
import com.nadosunbae_android.domain.model.classroom.ClassRoomData
import com.nadosunbae_android.domain.model.main.SelectableData
import com.nadosunbae_android.domain.model.mypage.MyPageBlockUpdateItem
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class SeniorPersonalFragment :
    BaseFragment<FragmentSeniorPersonalBinding>(R.layout.fragment_senior_personal) {
    private lateinit var classRoomQuestionMainAdapter: ClassRoomQuestionMainAdapter
    private lateinit var callback: OnBackPressedCallback
    lateinit var informationDetailActivity : InformationDetailActivity
    private val mainViewModel: MainViewModel by sharedViewModel()

    private val seniorPersonalViewModel: SeniorPersonalViewModel by viewModel()



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initSeniorQuestion()
        goSeniorFragment()
        goQuestionWrite()
        questionSort()
        initQuestionSort()
        observeArray()
        goMyPageClassRoomReview()
        blockSenior()
        observeLoadingEnd()
    }
    //로딩 종료
    private fun observeLoadingEnd() {
        seniorPersonalViewModel.onLoadingEnd.observe(viewLifecycleOwner) {
            dismissLoading()
        }
    }


    //선배에게 온 1:1 질문 목록
    private fun initSeniorQuestion() {
        classRoomQuestionMainAdapter =
            ClassRoomQuestionMainAdapter(2, mainViewModel.userId.value ?: 0, 0)
        binding.rcSeniorPersonal.adapter = classRoomQuestionMainAdapter
        seniorPersonalViewModel.seniorQuestion.observe(viewLifecycleOwner) {
            if(it.isEmpty()){
                binding.textSeniorEmpty.visibility = View.VISIBLE
                binding.rcSeniorPersonal.visibility = View.GONE
            }else{
                binding.textSeniorEmpty.visibility = View.GONE
                binding.rcSeniorPersonal.visibility = View.VISIBLE
            }
            classRoomQuestionMainAdapter.setQuestionMain(it as MutableList<ClassRoomData>)
        }
    }

    //학과 후기로 이동
    private fun goMyPageClassRoomReview() {
        binding.clSeniorPersonalClassReview.setOnClickListener {
            val intent = Intent(requireActivity(), MyPageClassroomReviewActivity::class.java)
            intent.apply {
                putExtra("userId", seniorPersonalViewModel.seniorId.value)
                putExtra("userNickName", seniorPersonalViewModel.seniorPersonal.value?.nickname)
            }
            requireActivity().startActivity(intent)
        }


    }



    //선배 개인페이지 서버 통신
    private fun getSeniorPersonal(sort: String) {
        mainViewModel.seniorId.observe(viewLifecycleOwner) {
            Timber.d("seniorId: $it")
            showLoading()
            seniorPersonalViewModel.getSeniorPersonal(it)
            seniorPersonalViewModel.getSeniorQuestionList(it, sort)
        }

        seniorPersonalViewModel.seniorPersonal.observe(viewLifecycleOwner) {
            seniorPersonalViewModel.seniorId.value = it.userId
            binding.seniorPersonal = it
            if (it.secondMajorName == "미진입")
                binding.textSeniorPersonalSecondMajorStart.visibility = View.GONE
        }
    }

    //뒤로가기
    private fun goSeniorFragment() {
        binding.imgSeniorPersonalTitle.setOnClickListener {
            mainViewModel.classRoomFragmentNum.value = 7
        }

    }

    //작성창으로 이동
    private fun goQuestionWrite() {
        binding.btnGoQuestionWrite.setOnClickListener {
            Timber.d("isReviewedSenior: ${ReviewGlobals.isReviewed}")
            if (ReviewGlobals.isReviewed) {
                val intent = Intent(requireActivity(), QuestionWriteActivity::class.java)
                intent.apply {
                    putExtra("division", 0)
                    putExtra("majorId", mainViewModel.selectedMajor.value?.majorId)
                    putExtra("userId", seniorPersonalViewModel.seniorId.value)
                    Timber.d("answerId: ${seniorPersonalViewModel.seniorId.value}")
                    putExtra("postTypeId", 4)
                    putExtra("title", resources.getString(R.string.question_write_one_to_one))
                    putExtra("hintContent", getString(R.string.question_write_content_hint))
                }
                startActivity(intent)
            } else {
                CustomDialog(requireActivity()).reviewAlertDialog(requireActivity())

            }
        }
    }

    //선배 차단
    private fun blockSenior() {
        binding.imgSeniorPersonalProfileMenu.setOnClickListener {
            val questionDropDownList = mutableListOf<SelectableData>(
                SelectableData(1, "차단", false)
            )

            showCustomDropDown(
                seniorPersonalViewModel,
                binding.imgSeniorPersonalProfileMenu,
                160f.dpToPx,
                null,
                -1 * 16f.dpToPx,
                null,
                true,
                seniorPersonalViewModel.dropDownSelected.value!!.id,
                questionDropDownList
            )
        }


    }


    override fun onResume() {
        super.onResume()
        getSeniorPersonal("recent")
    }

    //최신순, 도움순 정렬
    private fun questionSort() {
        binding.btnSeniorPersonalArray.setOnClickListener {
            val questionDropDownList = mutableListOf<SelectableData>(
                SelectableData(1, getString(R.string.review_latest_order), true),
                SelectableData(2, getString(R.string.review_likes_order), false)
            )
            showCustomDropDown(
                seniorPersonalViewModel,
                binding.btnSeniorPersonalArray,
                160f.dpToPx,
                null,
                -1 * 16f.dpToPx,
                null,
                true,
                seniorPersonalViewModel.dropDownSelected.value!!.id,
                questionDropDownList
            )

        }
    }

    //첫 화면 최신순
    private fun initQuestionSort() {
        seniorPersonalViewModel.dropDownSelected.value = SelectableData(1, "최신순", true)
    }

    //최신순, 도움순 차단 변경
    private fun observeArray() {
        seniorPersonalViewModel.dropDownSelected.observe(viewLifecycleOwner) {
            val sortData = seniorPersonalViewModel.dropDownSelected.value
            if (sortData != null) {
                when (sortData.name) {
                    getString(R.string.ask_everyone_new) -> binding.btnSeniorPersonalArray.text =
                        getString(R.string.review_latest_order)
                    getString(R.string.review_likes_order) -> binding.btnSeniorPersonalArray.text =
                        getString(R.string.review_likes_order)
                    getString(R.string.block) -> {
                        blockDialog(
                            deleteUser = {seniorPersonalViewModel.postClassRoomBlockUpdate(
                                MyPageBlockUpdateItem(
                                    seniorPersonalViewModel.seniorId.value ?: 0
                                )
                            )}
                        )
                    }
                }

            }
            var sort = "recent"
            val sortText = seniorPersonalViewModel.dropDownSelected.value?.name
            if (sortText != null) {
                if (sortText == getString(R.string.ask_everyone_new))
                    sort = "recent"
                else if (sortText == getString(R.string.review_likes_order)) {
                    sort = "like"
                }

            }
            Timber.d("seniorQuestion  : $sort")
            showLoading()
            seniorPersonalViewModel.getSeniorQuestionList(mainViewModel.seniorId.value ?: 0, sort)
        }
    }


    //차단하기 알럿
    private fun blockDialog(
        deleteUser: () -> Unit
    ){
            CustomDialog(requireActivity()).genericDialog(
                CustomDialog.DialogData(
                    resources.getString(R.string.classroom_block_title),
                    resources.getString(R.string.classroom_block_agree),
                    resources.getString(R.string.question_detail_cancel)
                ),
                complete = {
                    deleteUser()

                    if(mainViewModel.divisionBlock.value == 1){
                        MainGlobals.infoBlock = 1
                        requireActivity().finish()
                    }else{
                        mainViewModel.classRoomFragmentNum.value = 7
                    }

                    Toast.makeText(requireActivity(), "해당 유저가 차단되었습니다.", Toast.LENGTH_SHORT).show()
                },
                cancel = {

                }
            )
    }



}