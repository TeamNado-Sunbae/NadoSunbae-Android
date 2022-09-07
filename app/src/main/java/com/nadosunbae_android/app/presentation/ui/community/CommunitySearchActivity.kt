package com.nadosunbae_android.app.presentation.ui.community

import android.os.Bundle
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.databinding.ActivityCommunitySearchBinding
import com.nadosunbae_android.app.presentation.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CommunitySearchActivity :
    BaseActivity<ActivityCommunitySearchBinding>(R.layout.activity_community_search) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        clickBackBtn()
    }


    //뒤로가기 버튼
    private fun clickBackBtn(){
        binding.imgCommunitySearchArrow.setOnClickListener {
            finish()
        }

    }
}