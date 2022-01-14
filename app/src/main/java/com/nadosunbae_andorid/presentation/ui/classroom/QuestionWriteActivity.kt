package com.nadosunbae_andorid.presentation.ui.classroom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nadosunbae_andorid.R
import com.nadosunbae_andorid.databinding.ActivityQuestionWriteBinding
import com.nadosunbae_andorid.presentation.base.BaseActivity
import com.nadosunbae_andorid.presentation.ui.classroom.viewmodel.QuestionWriteViewModel
import com.nadosunbae_andorid.presentation.ui.main.viewmodel.MainViewModel

class QuestionWriteActivity : BaseActivity<ActivityQuestionWriteBinding>(R.layout.activity_question_write) {
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
    }





    //제목 입력했을 때
    private fun writeTitle(){
        binding.etQuestionWriteAllTitle.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                questionWriteViewModel.title.value = s.toString().isNotEmpty()
            }
        })
    }

    // 본문 입력했을 때
    private fun writeContent(){
        binding.etQuestionWriteAllContent.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                questionWriteViewModel.content.value = s.toString().isNotEmpty()
            }
        })
    }


    //완료 버튼 활성화
    private fun completeBtnCheck(){
        questionWriteViewModel.completeBtn.observe(this){
            binding.textQuestionWriteAllBtn.isSelected = it
            //완료 버튼 누를 때
            if(it){
                binding.textQuestionWriteAllBtn.setOnClickListener {
                    //서버 통신 코드
                    finish()
                }
            }
        }

    }
}