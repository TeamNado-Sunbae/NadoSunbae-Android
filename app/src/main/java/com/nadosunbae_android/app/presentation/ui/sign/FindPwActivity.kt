package com.nadosunbae_android.app.presentation.ui.sign


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.databinding.ActivityFindPwBinding
import com.nadosunbae_android.app.presentation.base.BaseActivity


//4순위 뷰 -> 뷰만 만들어 놓음
class FindPwActivity : BaseActivity<ActivityFindPwBinding>(R.layout.activity_find_pw) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_pw)

        onView()
    }

    private fun onView() {
        binding.etFindpwEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                isEmailPattern()

                if (binding.etFindpwEmail.text.toString() == "") {
                    binding.imgFindpwEmailCancel.isSelected = false
                } else {
                    binding.imgFindpwEmailCancel.isSelected = true
                }
            }
        })

    }

    //이메일 정규식
    private fun isEmailPattern() {
        val pattern = Patterns.EMAIL_ADDRESS
        binding.clFindpwOk.isSelected = pattern.matcher(binding.etFindpwEmail.text).matches()
    }

}