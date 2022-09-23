package com.nadosunbae_android.app.presentation.ui.community

import android.os.Bundle
import androidx.activity.viewModels
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.databinding.ActivityCommunityWriteUpdateBinding
import com.nadosunbae_android.app.presentation.base.BaseActivity
import com.nadosunbae_android.app.presentation.ui.community.viewmodel.CommunityWriteUpdateViewModel
import com.nadosunbae_android.domain.model.community.CommunityWriteUpdateData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CommunityWriteUpdateActivity : BaseActivity<ActivityCommunityWriteUpdateBinding>(R.layout.activity_community_write_update) {
    private val communityWriteUpdateViewModel : CommunityWriteUpdateViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_community_write_update)
        initUpdateView()
    }


    //기본 뷰 구성
    private fun initUpdateView(){
        communityWriteUpdateViewModel.setUpdateData(intent.getParcelableExtra("updateData") ?: CommunityWriteUpdateData.DEFAULT)
        binding.communityWriteUpdateViewModel = communityWriteUpdateViewModel

    }
}