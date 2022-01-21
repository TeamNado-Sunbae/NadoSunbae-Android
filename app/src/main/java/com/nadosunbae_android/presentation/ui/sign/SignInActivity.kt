package com.nadosunbae_android.presentation.ui.sign

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nadosunbae_android.R
import com.nadosunbae_android.data.model.request.sign.RequestSignIn
import com.nadosunbae_android.databinding.ActivitySignInBinding
import com.nadosunbae_android.presentation.base.BaseActivity
import com.nadosunbae_android.presentation.ui.main.MainActivity
import com.nadosunbae_android.presentation.ui.sign.viewmodel.SignUpBasicInfoViewModel


class SignInActivity : BaseActivity<ActivitySignInBinding>(R.layout.activity_sign_in) {
    private lateinit var mainActivity: MainActivity
    private val signUpBasicInfoViewModel: SignUpBasicInfoViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return SignUpBasicInfoViewModel() as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        onViewId()
        moveFindPw()
        moveSignUp()
        onViewPw()

    }

    //id editText textwatcher
    private fun onViewId() {
        binding.etSignInId.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                if (binding.etSignInId.text.toString() == "") {
                    binding.imgSignInIdCancel.isSelected = false
                } else {
                    binding.imgSignInIdCancel.isSelected = true
                }

                isEmptyText()
            }
        })

        binding.imgSignInIdCancel.setOnClickListener {
            binding.etSignInId.setText(null)
            binding.clLogin.isSelected = false
        }
    }

    //pw editText textWatcher
    private fun onViewPw() {
        binding.etSignInPw.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                if (binding.etSignInPw.text.toString() == "") {
                    binding.imgSignInPwCancel.isSelected = false
                } else {
                    binding.imgSignInPwCancel.isSelected = true
                }

                isEmptyText()

            }
        })

        binding.imgSignInPwCancel.setOnClickListener {
            binding.etSignInPw.setText(null)
            binding.clLogin.isSelected = false

        }
    }

    //id editText & pw editText 빈칸 여부
    private fun isEmptyText() {
        if (binding.etSignInId.text.toString() != "" && binding.etSignInPw.text.toString() != "") {
            binding.clLogin.isSelected = true
            moveMainPage()
        } else {
            binding.clLogin.isSelected = false
        }
    }

    //회원가입 페이지로 이동
    private fun moveSignUp() {
        binding.textSignInSignup.setOnClickListener {
            startActivity(Intent(this, SignUpAgreementActivity::class.java))
        }
    }

    //비밀번호 찾기 페이지로 이동
    private fun moveFindPw() {
        binding.textSignInFindpw.setOnClickListener {
            startActivity(Intent(this, FindPwActivity::class.java))
        }
    }

    //로그인 버튼 클릭 이벤트
    private fun moveMainPage() {
        Log.d("SignUp", "서버 통신 성공!")

        //val token = mainActivity.task.result
        binding.clLogin.setOnClickListener {

            signUpBasicInfoViewModel.signIn(
                RequestSignIn(
                    signUpBasicInfoViewModel.email.value.toString(),
                    signUpBasicInfoViewModel.password.value.toString(),
                    signUpBasicInfoViewModel.deviceToken.value.toString()
                )
            )
            signUpBasicInfoViewModel.signIn.observe(this){its ->
                Log.d("its", its.success.toString())
                if(its.success) {
                    startActivity(Intent(this, MainActivity::class.java))
                }
            }
        }
    }

}