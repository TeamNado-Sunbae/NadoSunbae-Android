package com.nadosunbae_andorid.presentation.ui.sign

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import com.nadosunbae_andorid.R
import com.nadosunbae_andorid.databinding.ActivitySignInBinding
import com.nadosunbae_andorid.presentation.base.BaseActivity
import java.util.regex.Pattern

class SignInActivity : BaseActivity<ActivitySignInBinding>(R.layout.activity_sign_in) {
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

                isEmailPattern()
                isEmptyText()
            }
        })

        binding.imgSignInIdCancel.setOnClickListener {
            binding.etSignInId.setText(null)
            binding.clLogin.isSelected = false
        }
    }

    //이메일 양식 체크
    private fun isEmailPattern() {
        val pattern: Pattern = Patterns.EMAIL_ADDRESS

        if (pattern.matcher(binding.etSignInId.text).matches()) {
            binding.clLogin.isSelected = true
        } else {
            binding.clLogin.isSelected = false
        }
    }


    //pw editText textWatcher
    private fun onViewPw() {
        binding.etSignInPw.addTextChangedListener(object : TextWatcher{
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

                isEmailPattern()
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
        if (binding.etSignInId.text.toString() != "" && binding.etSignInPw.text.toString()!="") {
            binding.clLogin.isSelected = true
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
}