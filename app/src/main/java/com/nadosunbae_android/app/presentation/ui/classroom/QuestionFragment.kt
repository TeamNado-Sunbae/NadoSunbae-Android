package com.nadosunbae_android.app.presentation.ui.classroom

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.databinding.FragmentQuestionBinding
import com.nadosunbae_android.app.presentation.base.BaseFragment
import com.nadosunbae_android.app.presentation.ui.classroom.adapter.ClassRoomQuestionMainAdapter
import com.nadosunbae_android.app.presentation.ui.main.MainGlobals
import com.nadosunbae_android.app.presentation.ui.main.viewmodel.MainViewModel
import com.nadosunbae_android.app.presentation.ui.review.ReviewGlobals
import com.nadosunbae_android.app.util.CustomDialog
import com.nadosunbae_android.domain.model.classroom.ClassRoomData
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber


class QuestionFragment : BaseFragment<FragmentQuestionBinding>(R.layout.fragment_question) {
    private val mainViewModel: MainViewModel by sharedViewModel()


    private lateinit var classRoomQuestionMainAdapter : ClassRoomQuestionMainAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initQuestionMain()
        changeAskEveryOne()
        changeSeniorFragment()
        goQuestionWriteAll()
        observeLoadingEnd()
    }

    override fun onResume() {
        super.onResume()
        mainViewModel.selectedMajor.observe(viewLifecycleOwner){
            mainViewModel.getClassRoomMain(3,it.majorId,"recent")
        }
    }

    //로딩 종료
    private fun observeLoadingEnd() {
        mainViewModel.onLoadingEnd.observe(viewLifecycleOwner) {
            dismissLoading()
        }
    }

    //질문 메인 데이터 받아오기
    private fun initQuestionMain(){
        mainViewModel.selectedMajor.observe(viewLifecycleOwner){
            Timber.d("QuestionMainMajorId: ${it.majorId}")
            showLoading()
            mainViewModel.getClassRoomMain(3,it.majorId, "recent")
        }

        classRoomQuestionMainAdapter = ClassRoomQuestionMainAdapter(1, mainViewModel.userId.value ?: 0,0)
        binding.rcQuestionAll.adapter = classRoomQuestionMainAdapter
        mainViewModel.classRoomMain.observe(viewLifecycleOwner){
            classRoomQuestionMainAdapter.setQuestionMain(it as MutableList<ClassRoomData>)
            visibleQuestion()
        }

    }

    //데이터 개수에 따라 뷰 보이기 설정
    private fun visibleQuestion(){
        if(classRoomQuestionMainAdapter.questionMainData.size == 0){
            with(binding){
                rcQuestionAll.visibility = View.GONE
                textQuestionAllGo.visibility = View.GONE
                textQuestionAllNoComment.visibility = View.VISIBLE
            }
        }else if(classRoomQuestionMainAdapter.questionMainData.size in 1..5){
            with(binding){
                rcQuestionAll.visibility = View.VISIBLE
                textQuestionAllGo.visibility = View.GONE
                textQuestionAllNoComment.visibility = View.GONE
            }
        }else{
            with(binding){
                rcQuestionAll.visibility = View.VISIBLE
                textQuestionAllGo.visibility = View.VISIBLE
                textQuestionAllNoComment.visibility = View.GONE
            }
        }
    }

    //전체에게 질문으로 이동
    private fun changeAskEveryOne(){
        binding.textQuestionAllGo.setOnClickListener {
            mainViewModel.classRoomFragmentNum.value = 2
        }
    }

    //질문 구성원 목록으로 이동
    private fun changeSeniorFragment(){
        binding.clSearchSenior.setOnClickListener {
            mainViewModel.classRoomFragmentNum.value = 3
        }
    }

    //전체 질문 작성으로 이동
    // 질문 전체(3)
    private fun goQuestionWriteAll(){
        binding.clQuestionWrite.setOnClickListener {
            //부적절 사용자
            if(MainGlobals.signInData!!.isReviewInappropriate || MainGlobals.signInData!!.isUserReported){
                CustomDialog(requireActivity()).genericDialog(
                    CustomDialog.DialogData(
                        MainGlobals.signInData!!.message,
                        resources.getString(R.string.sign_in_question),
                        resources.getString(R.string.email_certification_close)
                    ),
                    complete = {
                        var intent = Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.question_kakao)))
                        startActivity(intent)
                    },
                    cancel = {}
                )
            }else{
                if(ReviewGlobals.isReviewed ){
                    val intent = Intent(requireActivity(), QuestionWriteActivity::class.java)
                    intent.apply {
                        putExtra("division", 0)
                        putExtra("postTypeId", 3)
                        putExtra("majorId", mainViewModel.selectedMajor.value?.majorId)
                        putExtra("title", "전체에게 질문 작성")
                        putExtra("hintContent", getString(R.string.question_write_content_hint))
                    }
                    startActivity(intent)
                }else{
                    CustomDialog(requireActivity()).reviewAlertDialog(requireActivity())
                }
            }

        }
    }


}