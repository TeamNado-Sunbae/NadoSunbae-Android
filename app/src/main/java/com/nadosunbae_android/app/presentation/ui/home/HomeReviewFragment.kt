package com.nadosunbae_android.app.presentation.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.databinding.FragmentHomeReviewBinding
import com.nadosunbae_android.app.presentation.base.BaseFragment
import com.nadosunbae_android.app.presentation.ui.home.adpter.ReviewDetailAdapter
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class HomeReviewFragment : BaseFragment<FragmentHomeReviewBinding>(R.layout.fragment_home_review) {

    private val homeViewModel : HomeViewModel by viewModels()
    private lateinit var reviewDetailAdapter : ReviewDetailAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        naviControl()
        initNetwork()
    }

    private fun naviControl() {
        binding.ivHomeReviewBack.setOnClickListener {
            findNavController().navigate(R.id.action_homeReviewFragment_to_homeFragment)
        }
    }

    private fun initNetwork() {
        reviewDetailAdapter = ReviewDetailAdapter()
        binding.rvHomeReview.adapter = reviewDetailAdapter
        homeViewModel.getReviewDetail(1)
        homeViewModel.reviewDetail.observe(viewLifecycleOwner) {
            Timber.e("$it")
            (binding.rvHomeReview.adapter as ReviewDetailAdapter).submitList(it)
        }
    }

}