package com.nadosunbae_android.app.presentation.ui.community

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.preferences.protobuf.ListValueOrBuilder
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.databinding.FragmentCommunityMainContentBinding
import com.nadosunbae_android.app.presentation.base.BaseFragment


class CommunityMainContentFragment :
    BaseFragment<FragmentCommunityMainContentBinding>(R.layout.fragment_community_main_content) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSwipeTab()
        clickSwipeTab()
    }


    //탭 부분
    private fun initSwipeTab() {
        binding.customSwitchTab.switchText = mutableListOf("전체", "자유", "질문", "정보")
    }

    //탭 클릭
    private fun clickSwipeTab() {

        with(binding.customSwitchTab) {
            switchTab = listOf(true, false, false, false)
            itemClickListener = {
                switchTab = when (it) {
                    0 -> listOf(true, false, false, false)

                    1 -> listOf(false, true, false, false)

                    2 -> listOf(false, false, true, false)

                    else -> listOf(false, false, false, true)
                }
            }
        }
    }
}