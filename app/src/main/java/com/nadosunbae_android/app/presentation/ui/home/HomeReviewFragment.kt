package com.nadosunbae_android.app.presentation.ui.home

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.databinding.FragmentHomeReviewBinding
import com.nadosunbae_android.app.presentation.base.BaseFragment
import com.nadosunbae_android.app.presentation.ui.home.adpter.ReviewDetailAdapter
import com.nadosunbae_android.app.presentation.ui.main.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class HomeReviewFragment : BaseFragment<FragmentHomeReviewBinding>(R.layout.fragment_home_review) {

    private val mainViewModel : MainViewModel by activityViewModels()
    private val homeViewModel : HomeViewModel by viewModels()
    private lateinit var reviewDetailAdapter : ReviewDetailAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        naviControl()
        initNetwork()
        pressedBackButton()
    }

    private fun naviControl() {
        binding.ivHomeReviewBack.setOnClickListener {
            findNavController().navigate(R.id.action_homeReviewFragment_to_homeFragment)
        }
    }

    private fun initNetwork() {
        Timber.e("UserIdTest2: ${mainViewModel.userId.value}")
        Timber.e("UserUnivIdTest2 : ${mainViewModel.univId.value}")
        reviewDetailAdapter = ReviewDetailAdapter(mainViewModel.univId.value ?: 0)
        binding.rvHomeReview.adapter = reviewDetailAdapter
        homeViewModel.getReviewDetail(mainViewModel.univId.value ?: 0)
        homeViewModel.reviewDetail.observe(viewLifecycleOwner) {
            (binding.rvHomeReview.adapter as ReviewDetailAdapter).submitList(it)
        }
    }

    //뒤로가기 버튼
    private fun pressedBackButton(){
        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                NavHostFragment.findNavController(this@HomeReviewFragment).navigateUp()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, onBackPressedCallback)
    }
}