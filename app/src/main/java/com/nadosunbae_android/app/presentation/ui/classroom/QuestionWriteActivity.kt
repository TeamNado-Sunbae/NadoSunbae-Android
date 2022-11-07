package com.nadosunbae_android.app.presentation.ui.classroom

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.databinding.ActivityQuestionWriteBinding
import com.nadosunbae_android.app.presentation.base.BaseActivity
import com.nadosunbae_android.app.presentation.ui.classroom.viewmodel.QuestionWriteViewModel
import com.nadosunbae_android.app.util.CustomDialog
import com.nadosunbae_android.app.util.FirebaseAnalyticsUtil
import com.nadosunbae_android.domain.model.classroom.WriteUpdateItem
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber

@AndroidEntryPoint
class QuestionWriteActivity :
    BaseActivity<ActivityQuestionWriteBinding>(R.layout.activity_question_write) {
    private val questionWriteViewModel: QuestionWriteViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setWriteData()
        writeTitle()
        writeContent()
        completeBtnCheck()
        cancelWrite()
        questionWriteViewModel.setCompleteButton()
        clickOkButton()
        completeWriteOk()
    }
    //majorId, answerId 세팅
    private fun setWriteData(){
        with(questionWriteViewModel){
            majorId.value = intent.getIntExtra("majorId",-1)
            answerId.value = intent.getIntExtra("userId",-1)
        }

    }


    //제목 입력했을 때
    private fun writeTitle() {
        binding.questionWriteViewModel = questionWriteViewModel
        binding.etQuestionWriteAllTitle.addTextChangedListener {
            questionWriteViewModel.title.value = it.toString()
        }
    }

    // 본문 입력했을 때
    private fun writeContent() {
        binding.etQuestionWriteAllContent.addTextChangedListener {
            questionWriteViewModel.content.value = it.toString()
        }
    }

    //완료 버튼 활성화
    private fun completeBtnCheck() {
        questionWriteViewModel.completeButton.flowWithLifecycle(lifecycle)
            .onEach {
                binding.btnQuestionWriteOk.isEnabled = it
            }
            .launchIn(lifecycleScope)
    }

    //완료 버튼 클릭
    private fun clickOkButton() {
        binding.btnQuestionWriteOk.setOnClickListener {
            initCompleteDialog()
        }
    }
    //작성 완료시 상세로 이동
    private fun completeWriteOk(){
        questionWriteViewModel.onLoadingEnd.observe(this){
            if(it){
                val intent = Intent(this, QuestionDetailActivity::class.java)
                intent.putExtra("postId", questionWriteViewModel.postWrite.value.postId)
                startActivity(intent)
                finish()
            }
        }


    }


    //종료 버튼 클릭
    private fun cancelWrite() {
        binding.imgQuestionWriteAllCancle.setOnClickListener {
            initCancelDialog()
        }
    }

    //작성취소 다이얼로그 띄우기
    private fun initCancelDialog() {
        CustomDialog(this).genericDialog(
            dialogText = CustomDialog.DialogData(
                getString(R.string.question_update_cancel),
                getString(R.string.mypage_modify_alert_back_continue),
                getString(R.string.signup_alert_out)
            ),
            complete = {},
            cancel = { finish() }
        )
    }


    //작성 완료 다이얼로그 띄우기
    private fun initCompleteDialog() {
        CustomDialog(this).genericDialog(
            dialogText = CustomDialog.DialogData(
                getString(R.string.question_write_complete),
                getString(R.string.alert_write_review_complete),
                getString(R.string.question_write_complete_no)
            ),
            complete = {
                FirebaseAnalyticsUtil.firebaseLog("question_write_1on1","type","question_1on1_upload")
                questionWriteViewModel.postClassRoomWrite()
            },
            cancel = {}
        )
    }



    //백버튼
    override fun onBackPressed() {
        // super.onBackPressed()
        initCancelDialog()
    }
}