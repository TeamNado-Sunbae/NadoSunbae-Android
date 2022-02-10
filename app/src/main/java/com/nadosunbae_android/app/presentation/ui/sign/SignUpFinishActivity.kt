package com.nadosunbae_android.app.presentation.ui.sign

import android.content.Intent
import android.os.Bundle
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.databinding.ActivitySignUpFinishBinding
import com.nadosunbae_android.app.presentation.base.BaseActivity
import com.nadosunbae_android.app.presentation.ui.main.MainActivity

class SignUpFinishActivity : BaseActivity<ActivitySignUpFinishBinding>(R.layout.activity_sign_up_finish) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        nextPage()
    }

    private fun nextPage() {
        binding.clSignupFinish.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}