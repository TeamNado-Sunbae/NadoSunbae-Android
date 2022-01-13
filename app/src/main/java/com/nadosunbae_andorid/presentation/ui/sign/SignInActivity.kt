package com.nadosunbae_andorid.presentation.ui.sign

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.nadosunbae_andorid.R
import com.nadosunbae_andorid.databinding.ActivitySignInBinding
import com.nadosunbae_andorid.presentation.base.BaseActivity

class SignInActivity : BaseActivity<ActivitySignInBinding>(R.layout.activity_sign_in) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        onView()
        moveFindPw()
        moveSignUp()
    }

    private fun onView() {
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

                if (binding.etSignInPw.text.toString() == "") {
                    binding.imgSignInPwCancel.isSelected = false
                } else {
                    binding.imgSignInPwCancel.isSelected = true
                }

                if (binding.etSignInId.text.toString() != "" && binding.etSignInPw.text.toString()!="") {
                    binding.clLogin.isSelected = true
                } else {
                    binding.clLogin.isSelected = false
                }
            }
        })

    }

    private fun moveSignUp() {
        binding.textSignInSignup.setOnClickListener {
            startActivity(Intent(this, SignUpAgreementActivity::class.java))
        }
    }

    private fun moveFindPw() {
        binding.textSignInFindpw.setOnClickListener {
            startActivity(Intent(this, FindPwActivity::class.java))
        }
    }
}