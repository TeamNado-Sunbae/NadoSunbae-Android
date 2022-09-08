package com.nadosunbae_android.app.presentation.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.databinding.FragmentHomeQuestionBinding
import com.nadosunbae_android.app.presentation.base.BaseFragment
import com.nadosunbae_android.app.presentation.ui.community.adapter.CommunityMainContentAdapter
import com.nadosunbae_android.app.presentation.ui.community.viewmodel.CommunityViewModel
import com.nadosunbae_android.app.presentation.ui.home.adpter.QuestionDetailAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber

@AndroidEntryPoint
class HomeQuestionFragment :
    BaseFragment<FragmentHomeQuestionBinding>(R.layout.fragment_home_question) {

    private lateinit var questionDetailAdapter: QuestionDetailAdapter
    private val homeViewModel: HomeViewModel by viewModels()
    private val communityViewModel: CommunityViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        naviControl()
        setCommunityAdapter()

    }

    private fun naviControl() {
        binding.ivHomeQuestionBack.setOnClickListener {
            findNavController().navigate(R.id.action_homeQuestionFragment_to_homeFragment)
        }
    }

    //홈 뷰 커뮤니티 리사이클러뷰 연결
    private fun setCommunityAdapter() {
        //TODO: unviersityId 고정값
        communityViewModel.getCommunityMainData("1", "0", "questionToPerson", "recent","")
        questionDetailAdapter = QuestionDetailAdapter()
        binding.rvHomeQuestion.adapter = questionDetailAdapter
        communityViewModel.communityMainData.flowWithLifecycle(
            viewLifecycleOwner.lifecycle,
        ).onEach {
            Timber.e("$it")
            questionDetailAdapter.submitList(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }
}