package com.nadosunbae_android.app.presentation.ui.sign


import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import android.view.View
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.databinding.ActivityFindPwBinding
import com.nadosunbae_android.app.presentation.base.BaseActivity
import com.nadosunbae_android.app.presentation.ui.mypage.ChangePwFinishActivity
import com.nadosunbae_android.app.presentation.ui.mypage.viewmodel.MyPageViewModel
import com.nadosunbae_android.domain.model.mypage.MyPageResetPasswordItem
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber


class FindPwActivity : BaseActivity<ActivityFindPwBinding>(R.layout.activity_find_pw) {

    private val myPageViewModel: MyPageViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        emailTextWatcher()
        initBackBtn()

    }

    //비밀번호 변경 서버 통신
    private fun changePw() {
        Timber.d("ChangePw: 서버 통신 성공")
        myPageViewModel.resetPassword.observe(this) {
            if(!it.success) {
                Timber.d("비밀번호 서버통신 체크: 실패")
                binding.textFindPwWarn.visibility = View.VISIBLE
                binding.textFindPwOk.isSelected = false
                binding.imgFindPwCancel.isSelected = false
            }
            if(it.success) {
                Timber.d("비밀번호 서버통신 체크: 성공")
                binding.textFindPwWarn.visibility = View.INVISIBLE
                binding.textFindPwOk.isSelected = true
                binding.imgFindPwCancel.isSelected = true
                initNextBtn()
            }
        }
    }


    private fun emailTextWatcher() {
        binding.etFindPwEmail.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                if(binding.etFindPwEmail.text.toString() != "") {
                    binding.textFindPwOk.isSelected = true
                    binding.imgFindPwCancel.isSelected = true
                    initCancelBtn()
                    binding.textFindPwOk.setOnClickListener {
                        myPageViewModel.postMyPageRestPassword(MyPageResetPasswordItem(binding.etFindPwEmail.text.toString()))
                        changePw()
                    }
                } else {
                    //isEmailPattern()
                    binding.textFindPwOk.isSelected = false
                    binding.imgFindPwCancel.isSelected = false
                    binding.textFindPwWarn.visibility = View.INVISIBLE

                }
            }

        })
    }

    //textfield x버튼 클릭 이벤트
    private fun initCancelBtn() {
        binding.imgFindPwCancel.setOnClickListener {
            binding.etFindPwEmail.setText(null)
        }
    }

    // 뒤로가기 버튼 클릭 이벤트
    private fun initBackBtn() {
        binding.imgFindPwBack.setOnClickListener {
            finish()
        }
    }


    //이메일 정규식 -> 릴리즈 직전에 함수 활성화
    private fun isEmailPattern() {
        binding.etFindPwEmail.isSelected =
            binding.etFindPwEmail.text.contains("@korea.ac.kr")
        binding.textFindPwOk.isSelected = true
    }

    private fun initNextBtn() {
        val intent = Intent(this, ChangePwFinishActivity::class.java)
        intent.putExtra("email", binding.etFindPwEmail.text.toString())
        startActivity(intent)
        finish()
    }
}