package com.nadosunbae_android.app.presentation.ui.home


import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.databinding.FragmentHomeRankingBinding
import com.nadosunbae_android.app.presentation.base.BaseFragment
import com.nadosunbae_android.app.presentation.ui.classroom.question.DataToFragment
import com.nadosunbae_android.app.presentation.ui.home.adpter.RankingDetailAdapter
import com.nadosunbae_android.app.presentation.ui.main.MainGlobals
import com.nadosunbae_android.app.presentation.ui.main.viewmodel.MainViewModel
import com.nadosunbae_android.domain.model.home.HomeRankingData
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.internal.toImmutableList
import timber.log.Timber


@AndroidEntryPoint
class HomeRankingFragment :
    BaseFragment<FragmentHomeRankingBinding>(R.layout.fragment_home_ranking) {
    var link = SeniorDataToFragment()
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
            //findNavController().navigate(R.id.action_homeRankingFragment_to_homeFragment)
            mainViewModel.homeFragmentNum.value = 2
            mainViewModel.initLoading.value = true
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
        rankingDetailAdapter = RankingDetailAdapter(MainGlobals.signInData?.userId ?: 0, link)
        binding.rvHomeReview.adapter = rankingDetailAdapter
        homeViewModel.getHomeRanking(mainViewModel.univId.value ?: 0)
        homeViewModel.rankingData.observe(viewLifecycleOwner) {
            (binding.rvHomeReview.adapter as RankingDetailAdapter).submitList(it.subList(0,30))

        }
    }

    inner class SeniorDataToFragment : DataToFragment {
        override fun getSeniorId(seniorId: Int){
            mainViewModel.seniorId.value = seniorId
            goMyPage(seniorId)
        }
    }

    //선배 Id = userId가 같을 경우 마이페이지로 이동
    private fun goMyPage(seniorId : Int){
        val userId = mainViewModel.userId.value ?: 0
        if(userId == seniorId){
            mainViewModel.bottomNavItem.value = 4
        }else{
            mainViewModel.homeFragmentNum.value = 1
            mainViewModel.initLoading.value = true
        }
        mainViewModel.seniorBack.value = 1
    }

}