package com.nadosunbae_andorid.presentation.ui.sign

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.nadosunbae_andorid.R
import com.nadosunbae_andorid.databinding.ActivitySignUpFinishBinding
import com.nadosunbae_andorid.presentation.base.BaseActivity
import com.nadosunbae_andorid.presentation.ui.main.MainActivity

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