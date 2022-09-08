package com.nadosunbae_android.app.presentation.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.databinding.FragmentHomeBinding
import com.nadosunbae_android.app.presentation.base.BaseFragment
import com.nadosunbae_android.app.presentation.ui.community.adapter.CommunityMainContentAdapter
import com.nadosunbae_android.app.presentation.ui.community.viewmodel.CommunityViewModel
import com.nadosunbae_android.app.presentation.ui.home.adpter.BannerListAdapter
import com.nadosunbae_android.app.presentation.ui.home.adpter.QuestionAdapter
import com.nadosunbae_android.app.presentation.ui.home.adpter.ReviewAdapter
import com.nadosunbae_android.app.presentation.ui.main.viewmodel.MainViewModel
import com.nadosunbae_android.app.util.imageSelect
import com.nadosunbae_android.domain.model.home.HomeUnivReviewData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber


@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    private var reviewList = mutableListOf<HomeUnivReviewData>()
    private val homeViewModel: HomeViewModel by viewModels()
    private val communityViewModel: CommunityViewModel by viewModels()

    private val mainViewModel: MainViewModel by activityViewModels()
    private lateinit var bannerAdapter: BannerListAdapter
    private lateinit var reviewAdapter: ReviewAdapter
    private lateinit var communityMainContentAdapter: CommunityMainContentAdapter

    //TODO: 랭킹 클릭 시 선배 프로필로 이동

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setReviewAdapter()
        setQuestionAdapter()
        setCommunityAdapter()
        naviControl()
        setBanner()
        setRanking()
    }

    private fun setBanner() {
//        bannerAdapter = BannerListAdapter(homeViewModel.BannerData.subList(1, 1))
//        binding.vpHomeBanner.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
//            override fun onPageSelected(position: Int) {
//                super.onPageSelected(position)
//                if (position == 0) {
//                    Timber.d("TEST1")
//                }
//                else if (position == 1) {
//                    Timber.d("TEST2")
//                }
//                else if (position == 2){
//                    Timber.d("TEST3")
//                }
//            }
//        })
    }

    //홈 뷰 리뷰 리사이클러뷰 연결
    private fun setReviewAdapter() {
        //TODO : 후기 미작성 유저 알럿 확인 필요
        Timber.e("UserIdTest1: ${mainViewModel.userId.value}")
        Timber.e("UserUnivIdTest1 : ${mainViewModel.univId.value}")
        reviewAdapter = ReviewAdapter(mainViewModel.userId.value ?: 0)
        binding.rvHomeReview.adapter = reviewAdapter
        homeViewModel.getReviewDetail(mainViewModel.univId.value ?: 0)

        //홈 뷰에서는 item 5개만 띄우기
        homeViewModel.reviewDetail.observe(viewLifecycleOwner) {
            (binding.rvHomeReview.adapter as ReviewAdapter).submitList(it.subList(0, 5))
        }
    }

    //홈 뷰 질문 리사이클러뷰 연결
    private fun setQuestionAdapter() {
        binding.rvHomeQuestion.adapter = QuestionAdapter()
        (binding.rvHomeQuestion.adapter as QuestionAdapter).submitList(homeViewModel.questionData)
    }

    //홈 뷰 커뮤니티 리사이클러뷰 연결
    private fun setCommunityAdapter() {
        communityViewModel.getCommunityMainData("${mainViewModel.univId}", "", "community", "recent")
        communityMainContentAdapter = CommunityMainContentAdapter()
        binding.rvHomeCommunity.adapter = communityMainContentAdapter
        communityViewModel.communityMainData.flowWithLifecycle(
            viewLifecycleOwner.lifecycle,
        ).onEach {
            if(it.size > 3) {
                communityMainContentAdapter.submitList(it.subList(0,3))
            } else {
                communityMainContentAdapter.submitList(it)
            }
        }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    //navi control
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

        binding.tvHomeCommunityMore.setOnClickListener {
            //TODO : 커뮤니티 탭으로 이동
        }
    }

    //시상대 data
    private fun setRanking() {
        homeViewModel.getHomeRanking(mainViewModel.univId.value ?: 0)
        homeViewModel.rankingData.observe(viewLifecycleOwner) {
            binding.apply {
                textNicknameRanking1.text = it[0].nickname
                textRateRanking1.text = "응답률 ${it[0].rate}%"
                imgRateRanking1.setImageResource(imageSelect(it[0].profileImageId))

                textNicknameRanking2.text = it[1].nickname
                textRateRanking2.text = "응답률 ${it[1].rate}%"
                imgRateRanking2.setImageResource(imageSelect(it[1].profileImageId))

                textNicknameRanking3.text = it[2].nickname
                textRateRanking3.text = "응답률 ${it[2].rate}%"
                imgRateRanking3.setImageResource(imageSelect(it[2].profileImageId))

                textNicknameRanking4.text = it[3].nickname
                textRateRanking4.text = "응답률 ${it[3].rate}%"
                imgRateRanking4.setImageResource(imageSelect(it[3].profileImageId))

                textNicknameRanking5.text = it[4].nickname
                textRateRanking5.text = "응답률 ${it[4].rate}%"
                imgRateRanking5.setImageResource(imageSelect(it[4].profileImageId))
            }

        }
    }
}