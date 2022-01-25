package com.nadosunbae_android.presentation.ui.classroom

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nadosunbae_android.R
import com.nadosunbae_android.model.request.classroom.RequestClassRoomPostData
import com.nadosunbae_android.databinding.ActivityQuestionWriteBinding
import com.nadosunbae_android.presentation.base.BaseActivity
import com.nadosunbae_android.presentation.ui.classroom.viewmodel.QuestionWriteViewModel
import com.nadosunbae_android.util.CustomDialog

class QuestionWriteActivity :
    BaseActivity<ActivityQuestionWriteBinding>(R.layout.activity_question_write) {
    private lateinit var dialog : CustomDialog
    private val questionWriteViewModel: QuestionWriteViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return QuestionWriteViewModel() as T
            }
        }
    }
    var title = false
    var content = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        writeTitle()
        writeContent()
        completeBtnCheck()
        cancelWrite()
        titleChange()
    }


    //제목 입력했을 때
    private fun writeTitle() {
        binding.etQuestionWriteAllTitle.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                questionWriteViewModel.title.value = s.toString().isNotEmpty()
                questionWriteViewModel.titleData.value = s.toString()
                binding.viewQuestionWriteAllTitleLineGray.isVisible = false
                binding.viewQuestionWriteAllTitleLineBlack.isVisible = true
            }
        })
    }

    // 본문 입력했을 때
    private fun writeContent() {
        binding.etQuestionWriteAllContent.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                binding.etQuestionWriteAllTitle.isSelected = false
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                questionWriteViewModel.content.value = s.toString().isNotEmpty()
                questionWriteViewModel.contentData.value = s.toString()
                binding.viewQuestionWriteAllTitleLineBlack.isVisible = false
                binding.viewQuestionWriteAllTitleLineGray.isVisible = true
            }
        })
    }


    //완료 버튼 활성화
    private fun completeBtnCheck() {
        questionWriteViewModel.completeBtn.observe(this) {
            binding.textQuestionWriteAllBtn.isSelected = it
            //완료 버튼 누를 때
            if (it) {
                binding.textQuestionWriteAllBtn.setOnClickListener {
                    initCompleteDialog()
                }
            }
        }
    }
    //제목 변경
    private fun titleChange(){
        val title = intent.getStringExtra("title")
        binding.textQuestionWriteAllTitle.text = title.toString()

    }


    //종료 버튼 클릭
    private fun cancelWrite(){
        binding.imgQuestionWriteAllCancle.setOnClickListener {
            initCancelDialog()
        }
    }

    //작성취소 다이얼로그 띄우기
    private fun initCancelDialog(){
        dialog = CustomDialog(this)
        dialog.writeCancelDialog(R.layout.dialog_question_write_cancel)
        dialog.setOnClickedListener(object : CustomDialog.ButtonClickListener{
            override fun onClicked(num: Int) {
                if(num == 1) finish()
            }
        })
    }

    //작성 완료 다이얼로그 띄우기
    private fun initCompleteDialog(){
        dialog = CustomDialog(this)
        dialog.writeCompleteDialog(R.layout.dialog_question_write_complete)
        dialog.setOnClickedListener(object : CustomDialog.ButtonClickListener{
            override fun onClicked(num: Int) {
                if(num == 2) selectQuestionWrite()
            }
        })
    }
    //작성 서버통신 분기처리( 2-> 정보, 3-> 질문(전체), 4 -> 질문(1:1)
    private fun selectQuestionWrite(){
        val postTypeId = intent.getIntExtra("postTypeId", 1)
        val answerId = intent.getIntExtra("userId", 1)
        val majorId = intent.getIntExtra("majorId",5)


        when(postTypeId){
            2 -> questionWrite(majorId, null, 2)
            3 -> questionWrite(majorId,null, 3)
            4 -> questionWrite(majorId, answerId, 4)
        }

    }


    //작성 서버통신
    private fun questionWrite(majorId : Int,answerId : Int?, postTypeId : Int){
        Log.d("나 서버통신", "나 강림")
        questionWriteViewModel.postClassRoomWrite(
            RequestClassRoomPostData(
                majorId, answerId, postTypeId,
                questionWriteViewModel.titleData.value.toString(),
                questionWriteViewModel.contentData.value.toString()
            )
        )
        questionWriteViewModel.postDataWrite.observe(this){its ->
            Log.d("its", its.success.toString())
            if(its.success){
                finish()
            }
        }

    }
}