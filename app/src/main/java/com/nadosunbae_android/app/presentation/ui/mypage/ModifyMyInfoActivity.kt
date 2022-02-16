package com.nadosunbae_android.app.presentation.ui.mypage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.databinding.ActivityModifyMyInfoBinding
import com.nadosunbae_android.app.presentation.base.BaseActivity
import com.nadosunbae_android.app.presentation.ui.mypage.viewmodel.MyPageViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ModifyMyInfoActivity :
    BaseActivity<ActivityModifyMyInfoBinding>(R.layout.activity_modify_my_info) {
    private val myPageViewModel: MyPageViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modify_my_info)
    }
}