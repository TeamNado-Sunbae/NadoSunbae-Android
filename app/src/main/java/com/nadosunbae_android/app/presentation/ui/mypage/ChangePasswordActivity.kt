package com.nadosunbae_android.app.presentation.ui.mypage

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.databinding.ActivityChangePasswordBinding
import com.nadosunbae_android.app.presentation.base.BaseActivity
import com.nadosunbae_android.app.presentation.ui.mypage.viewmodel.MyPageViewModel
import com.nadosunbae_android.app.presentation.ui.sign.SignInActivity
import com.nadosunbae_android.domain.model.mypage.MyPageResetPasswordItem
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class ChangePasswordActivity : BaseActivity<ActivityChangePasswordBinding>(R.layout.activity_change_password) {

    private val myPageViewModel: MyPageViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        emailTextWatcher()
        initBackBtn()

    }

    //비밀번호 변경 서버 통신
    private fun changePw() {
        Timber.d("ChangePw : 서버 통신 성공")
        myPageViewModel.resetPassword.observe(this) {
            if(!it.success) {
                Timber.d("비밀번호 서버통신 체크 : 실패")
                binding.textChangePwWarn.visibility = View.VISIBLE
                binding.textChangePwOk.isSelected = false
                binding.imgChangePwCancel.isSelected = false
            }
            if(it.success) {
                Timber.d("비밀번호 서버통신 체크 : 성공")
                binding.textChangePwWarn.visibility = View.INVISIBLE
                binding.textChangePwOk.isSelected = true
                binding.imgChangePwCancel.isSelected = true
                initNextBtn()
            }
        }
    }


    private fun emailTextWatcher() {
        binding.etChangePwEmail.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                if(binding.etChangePwEmail.text.toString() != "") {
                    binding.textChangePwOk.isSelected = true
                    binding.imgChangePwCancel.isSelected = true
                    initCancelBtn()
                    binding.textChangePwOk.setOnClickListener {
                        myPageViewModel.postMyPageRestPassword(MyPageResetPasswordItem(binding.etChangePwEmail.text.toString()))
                        changePw()
                    }
                } else {
                    //isEmailPattern()
                    binding.textChangePwOk.isSelected = false
                    binding.imgChangePwCancel.isSelected = false
                    binding.textChangePwWarn.visibility = View.INVISIBLE

                }
            }

        })
    }

    //textfield x버튼 클릭 이벤트
    private fun initCancelBtn() {
        binding.imgChangePwCancel.setOnClickListener {
            binding.etChangePwEmail.setText(null)
        }
    }

    // 뒤로가기 버튼 클릭 이벤트
    private fun initBackBtn() {
        binding.imgChangePwBack.setOnClickListener {
            finish()
        }
    }


    //이메일 정규식 -> 릴리즈 직전에 함수 활성화
    private fun isEmailPattern() {
        binding.etChangePwEmail.isSelected =
            binding.etChangePwEmail.text.contains("@korea.ac.kr")
        binding.textChangePwOk.isSelected = true
    }

    private fun initNextBtn() {
        val intent = Intent(this, ChangePwFinishActivity::class.java)
        intent.putExtra("email", binding.etChangePwEmail.text.toString())
        startActivity(intent)
        finish()
    }
}