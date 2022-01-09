package com.nadosunbae_andorid.presentation.ui.sign

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.nadosunbae_andorid.R
import com.nadosunbae_andorid.databinding.ActivityFindPwBinding
import com.nadosunbae_andorid.presentation.base.BaseActivity

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
                //나중에 여기에 코드를 쓰겠지 ?
            }
        })

    }

}