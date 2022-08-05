package com.nadosunbae_android.app.presentation.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.databinding.FragmentHomeBinding
import com.nadosunbae_android.app.presentation.base.BaseFragment
import com.nadosunbae_android.app.presentation.ui.home.adpter.CommunityAdapter
import com.nadosunbae_android.app.presentation.ui.home.adpter.QuestionAdapter
import com.nadosunbae_android.app.presentation.ui.home.adpter.ReviewAdapter


class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val homeViewModel : homeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setReviewAdapter()
        setQuestionAdapter()
        setCommunityAdapter()
        naviControl()
    }

    //홈 뷰 리뷰 리사이클러뷰 연결
    private fun setReviewAdapter() {
        binding.rvHomeReview.adapter = ReviewAdapter()
        (binding.rvHomeReview.adapter as ReviewAdapter).submitList(homeViewModel.reviewData)
    }

    //홈 뷰 질문 리사이클러뷰 연결
    private fun setQuestionAdapter() {
        binding.rvHomeQuestion.adapter = QuestionAdapter()
        (binding.rvHomeQuestion.adapter as QuestionAdapter).submitList(homeViewModel.questionData)
    }

    //홈 뷰 커뮤니티 리사이클러뷰 연결
    private fun setCommunityAdapter() {
        binding.rvHomeCommunity.adapter = CommunityAdapter()
        (binding.rvHomeCommunity.adapter as CommunityAdapter).submitList(homeViewModel.communityData)
    }

    private fun naviControl() {
        binding.tvHomeReviewMore.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_homeReviewFragment)
        }

        binding.tvHomeNewQuestionMore.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_homeQuestionFragment)
        }

        binding.tvHomeRankingMore.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_homeRankingFragment)
        }
    }

}