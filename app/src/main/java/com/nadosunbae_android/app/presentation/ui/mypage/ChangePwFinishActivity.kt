package com.nadosunbae_android.app.presentation.ui.mypage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.databinding.ActivityChangePwFinishBinding
import com.nadosunbae_android.app.databinding.ActivitySignUpBasicInfoBinding
import com.nadosunbae_android.app.presentation.base.BaseActivity

class ChangePwFinishActivity :
    BaseActivity<ActivityChangePwFinishBinding>(R.layout.activity_change_pw_finish) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initLoginBtn()
        initResendBtn()

    }

    private fun initLoginBtn() {

    }

    private fun initResendBtn() {

    }
}