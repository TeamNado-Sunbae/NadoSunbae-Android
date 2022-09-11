package com.nadosunbae_android.app.presentation.ui.community

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.databinding.FragmentCommunityBinding
import com.nadosunbae_android.app.presentation.base.BaseFragment
import com.nadosunbae_android.app.util.changeFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CommunityFragment : BaseFragment<FragmentCommunityBinding>(R.layout.fragment_community) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initCommunityMainContent()
        goCommunitySearch()
    }



    //커뮤니티 메인 콘텐트 붙이기
    private fun initCommunityMainContent(){
        changeFragment(R.id.fragment_container_community, CommunityMainContentFragment() )
    }

    //커뮤니티 검색 탭 이동
    private fun goCommunitySearch(){
        binding.imgCommunitySearch.setOnClickListener {
            val intent = Intent(requireActivity(), CommunitySearchActivity::class.java)
            startActivity(intent)
        }
    }
}