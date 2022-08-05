package com.nadosunbae_android.app.presentation.ui.home


import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.databinding.FragmentHomeRankingBinding
import com.nadosunbae_android.app.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeRankingFragment :
    BaseFragment<FragmentHomeRankingBinding>(R.layout.fragment_home_ranking) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        naviControl()
        alertListener()

    }

    private fun naviControl() {
        binding.ivHomeReviewBack.setOnClickListener {
            findNavController().navigate(R.id.action_homeRankingFragment_to_homeFragment)
        }
    }

    private fun alertListener() {
        binding.ivRankingWarn.setOnClickListener {
            binding.clDialog.visibility = View.VISIBLE
        }

        binding.ivRankingX.setOnClickListener {
            binding.clDialog.visibility = View.INVISIBLE
        }
    }

}