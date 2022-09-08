package com.nadosunbae_android.app.presentation.ui.community

import android.os.Bundle
import android.view.KeyEvent
import android.view.KeyEvent.KEYCODE_ENTER
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.databinding.ActivityCommunitySearchBinding
import com.nadosunbae_android.app.presentation.base.BaseActivity
import com.nadosunbae_android.app.presentation.ui.community.adapter.CommunityMainContentAdapter
import com.nadosunbae_android.app.presentation.ui.community.viewmodel.CommunitySearchViewModel
import com.nadosunbae_android.app.util.closeKeyboard
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class CommunitySearchActivity :
    BaseActivity<ActivityCommunitySearchBinding>(R.layout.activity_community_search) {

    private val communitySearchViewModel: CommunitySearchViewModel by viewModels()
    private lateinit var communityMainContentAdapter: CommunityMainContentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        clickBackBtn()
        searchCommunityData()
        getCommunityData()
        initCommunitySearchView()
        clickCancelBtn()

    }

    //검색 뷰 변경
    private fun initCommunitySearchView() {
        communitySearchViewModel.communitySearchView.observe(this) {
            binding.communitySearchViewModel = communitySearchViewModel
        }
    }


    //검색 서버 통신
    private fun searchCommunityData() {
        binding.etCommunitySearch.setOnFocusChangeListener { view, b ->
            if (b) binding.cancel = true
        }
        binding.etCommunitySearch.setOnKeyListener { view, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KEYCODE_ENTER) {
                val searchWord = binding.etCommunitySearch.text.toString()
                communitySearchViewModel.getCommunitySearchData(Pair("1", searchWord))
                binding.cancel = false
                view.clearFocus()
                this.closeKeyboard(view)
                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }
    }


    //검색 데이터 받기
    private fun getCommunityData() {
        communityMainContentAdapter = CommunityMainContentAdapter()
        binding.rcCommunitySearch.adapter = communityMainContentAdapter
        communitySearchViewModel.communitySearchData.flowWithLifecycle(
            lifecycle,
            Lifecycle.State.STARTED
        )
            .onEach {
                communityMainContentAdapter.submitList(it)
            }
            .launchIn(lifecycleScope)
    }

    //취소 버튼
    private fun clickCancelBtn() {
        binding.imgCommunitySearchCancel.setOnClickListener {
            binding.etCommunitySearch.text.clear()
        }
    }

    //뒤로가기 버튼
    private fun clickBackBtn() {
        binding.imgCommunitySearchArrow.setOnClickListener {
            finish()
        }

    }
}