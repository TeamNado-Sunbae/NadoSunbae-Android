package com.nadosunbae_android.app.presentation.ui.home

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.databinding.FragmentHomeReviewBinding
import com.nadosunbae_android.app.presentation.base.BaseFragment


class HomeReviewFragment : BaseFragment<FragmentHomeReviewBinding>(R.layout.fragment_home_review) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        naviControl()

    }

    private fun naviControl() {
        binding.ivHomeReviewBack.setOnClickListener {
            findNavController().navigate(R.id.action_homeReviewFragment_to_homeFragment)
        }
    }

}