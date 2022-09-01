package com.nadosunbae_android.app.presentation.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.databinding.FragmentHomeBinding
import com.nadosunbae_android.app.presentation.base.BaseFragment
import com.nadosunbae_android.app.presentation.ui.home.adpter.*
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val homeViewModel : HomeViewModel by viewModels()
    private lateinit var bannerAdapter : BannerListAdapter
    private lateinit var reviewAdapter: ReviewAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setReviewAdapter()
        setQuestionAdapter()
        setCommunityAdapter()
        naviControl()
        setBanner()
    }

    private fun setBanner() {
        bannerAdapter = BannerListAdapter(homeViewModel.BannerData.subList(1, 1))
        binding.vpHomeBanner.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position == 0) {
                    Timber.d("TEST1")
                }
                else if (position == 1) {
                    Timber.d("TEST2")
                }
                else if (position == 2){
                    Timber.d("TEST3")
                }
            }
        })
    }

    //홈 뷰 리뷰 리사이클러뷰 연결
    private fun setReviewAdapter() {
        reviewAdapter = ReviewAdapter()
        binding.rvHomeReview.adapter = reviewAdapter
        homeViewModel.getReviewDetail(1)
        homeViewModel.reviewDetail.observe(viewLifecycleOwner) {
            (binding.rvHomeReview.adapter as ReviewAdapter).submitList(it)
        }
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