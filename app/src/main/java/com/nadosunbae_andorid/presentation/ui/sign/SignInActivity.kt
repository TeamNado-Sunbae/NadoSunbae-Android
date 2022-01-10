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
    }

    private fun onView() {
        binding.etSignInId.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                //나중에 여기에 코드를 쓰겠지 ?
            }
        })

    }

    private fun moveFindPw() {
        binding.textSignInFindpw.setOnClickListener {
            startActivity(Intent(this, FindPwActivity::class.java))
        }
    }
}