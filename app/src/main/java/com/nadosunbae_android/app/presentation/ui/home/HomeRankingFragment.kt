package com.nadosunbae_android.app.presentation.ui.home


import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.databinding.FragmentHomeRankingBinding
import com.nadosunbae_android.app.presentation.base.BaseFragment
import com.nadosunbae_android.app.presentation.ui.home.adpter.RankingDetailAdapter
import com.nadosunbae_android.app.presentation.ui.main.viewmodel.MainViewModel
import com.nadosunbae_android.domain.model.home.HomeRankingData
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.internal.toImmutableList


@AndroidEntryPoint
class HomeRankingFragment :
    BaseFragment<FragmentHomeRankingBinding>(R.layout.fragment_home_ranking) {

    private val mainViewModel : MainViewModel by activityViewModels()
    private val homeViewModel : HomeViewModel by viewModels()
    private lateinit var rankingDetailAdapter: RankingDetailAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        naviControl()
        alertListener()
        initSetting()

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

    private fun initSetting() {
        rankingDetailAdapter = RankingDetailAdapter()
        binding.rvHomeReview.adapter = rankingDetailAdapter
        homeViewModel.getHomeRanking(mainViewModel.univId.value ?: 0)
        homeViewModel.rankingData.observe(viewLifecycleOwner) {
            (binding.rvHomeReview.adapter as RankingDetailAdapter).submitList(it.subList(0,30))

        }
    }

}