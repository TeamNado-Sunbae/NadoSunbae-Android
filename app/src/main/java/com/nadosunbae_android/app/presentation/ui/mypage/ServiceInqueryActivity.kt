package com.nadosunbae_android.app.presentation.ui.mypage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nadosunbae_android.app.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ServiceInqueryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service_inquery)
    }
}