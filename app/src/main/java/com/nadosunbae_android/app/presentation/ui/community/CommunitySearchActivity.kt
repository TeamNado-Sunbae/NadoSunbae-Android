package com.nadosunbae_android.app.presentation.ui.community

import androidx.appcompat.app.AppCompatActivity
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
    }
}