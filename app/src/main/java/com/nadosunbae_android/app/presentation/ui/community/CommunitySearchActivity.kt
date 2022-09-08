package com.nadosunbae_android.app.presentation.ui.community

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.databinding.ActivityCommunitySearchBinding
import com.nadosunbae_android.app.presentation.base.BaseActivity
import com.nadosunbae_android.app.presentation.ui.community.adapter.CommunityMainContentAdapter
import com.nadosunbae_android.app.presentation.ui.community.viewmodel.CommunitySearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class CommunitySearchActivity :
    BaseActivity<ActivityCommunitySearchBinding>(R.layout.activity_community_search) {

    private val communitySearchViewModel: CommunitySearchViewModel by viewModels()
    private lateinit var communityMainContentAdapter : CommunityMainContentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        clickBackBtn()
        searchCommunityData()
        getCommunityData()
    }


    //검색 서버 통신
    private fun searchCommunityData() {
        binding.etCommunitySearch.addTextChangedListener {
            //Todo universityId 넣기
            communitySearchViewModel.debounce(Pair("1", it.toString()))
        }
    }

    //검색 데이터 받기
    private fun getCommunityData(){
        communityMainContentAdapter = CommunityMainContentAdapter()
        binding.rcCommunitySearch.adapter = communityMainContentAdapter
        communitySearchViewModel.communitySearchData.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach {
                   communityMainContentAdapter.submitList(it)
            }
            .launchIn(lifecycleScope)
    }

    //뒤로가기 버튼
    private fun clickBackBtn() {
        binding.imgCommunitySearchArrow.setOnClickListener {
            finish()
        }

    }
}