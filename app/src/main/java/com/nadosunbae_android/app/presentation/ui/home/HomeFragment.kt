package com.nadosunbae_android.app.presentation.ui.home

import android.os.Bundle
import android.view.View
import androidx.core.view.size
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
import com.nadosunbae_android.app.presentation.ui.home.adpter.BannerAdapter
import com.nadosunbae_android.app.presentation.ui.home.adpter.QuestionAdapter
import com.nadosunbae_android.app.presentation.ui.home.adpter.ReviewAdapter
import com.nadosunbae_android.app.presentation.ui.main.MainGlobals
import com.nadosunbae_android.app.presentation.ui.main.viewmodel.MainViewModel
import com.nadosunbae_android.app.util.CustomDecoration
import com.nadosunbae_android.app.util.FirebaseAnalyticsUtil
import com.nadosunbae_android.app.util.dpToPxF
import com.nadosunbae_android.app.util.imageSelect
import com.nadosunbae_android.domain.model.home.HomeUnivReviewData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber
import java.lang.Math.ceil


@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    private var reviewList = mutableListOf<HomeUnivReviewData>()
    private val homeViewModel: HomeViewModel by viewModels()
    private val communityViewModel: CommunityViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()
    private lateinit var bannerAdapter: BannerAdapter
    private lateinit var questionAdapter: QuestionAdapter
    private lateinit var reviewAdapter: ReviewAdapter
    private lateinit var communityMainContentAdapter: CommunityMainContentAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //setReviewAdapter()
        goSeniorPage()
        // setQuestionAdapter()
        // setCommunityAdapter()
        naviControl()
        setBanner()
        setRanking()
        setUnivName()
        setAnalytics()
    }

    override fun onResume() {
        super.onResume()
        setReviewAdapter()
        setQuestionAdapter()
        setCommunityAdapter()
    }

    private fun setBanner() {
        homeViewModel.getAppBanner("AOS")

        homeViewModel.bannerData.observe(viewLifecycleOwner) {
            val images = it.map { it.imageUrl }
            val url = it.map { it.redirectUrl }
            bannerAdapter = BannerAdapter(requireContext(), images, url)
            binding.vpHomeBanner.adapter = bannerAdapter
            val bannerPosition = Int.MAX_VALUE / 4 - ceil(it.size.toDouble() / 4).toInt()
            binding.vpHomeBanner.setCurrentItem(bannerPosition, false)

            bannerAdapter.getBannerPositionListener { num ->
                val paramValueNum = Character.getNumericValue(num.toString().last())
                val paramList = listOf("banner_1", "banner_2", "banner_3")
                FirebaseAnalyticsUtil.firebaseLog(
                    "banner_click",
                    "number",
                    paramList[paramValueNum]
                )
            }
        }


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
        homeViewModel.getQuestionMainData(
            MainGlobals.signInData?.universityId ?: 1,
            "0",
            "questionToPerson",
            "recent"
        )
        questionAdapter = QuestionAdapter(mainViewModel.userId.value ?: 0)
        binding.rvHomeQuestion.adapter = questionAdapter
        homeViewModel.personToQuestionData.flowWithLifecycle(
            viewLifecycleOwner.lifecycle,
        ).onEach {
            if (it.size > 4) {
                questionAdapter.submitList(it.subList(0, 5))
            } else {
                questionAdapter.submitList(it)
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    //홈 뷰 커뮤니티 리사이클러뷰 연결
    private fun setCommunityAdapter() {
        communityViewModel.getCommunityMainData(
            MainGlobals.signInData?.universityId ?: 1,
            "",
            "community",
            "recent"
        )
        communityMainContentAdapter = CommunityMainContentAdapter()
        binding.rvHomeCommunity.adapter = communityMainContentAdapter
        val decoration =
            CustomDecoration(1.dpToPxF, 16.dpToPxF, requireContext().getColor(R.color.gray_0))
        binding.rvHomeCommunity.addItemDecoration(decoration)
        communityViewModel.communityMainFilterData.flowWithLifecycle(
            viewLifecycleOwner.lifecycle,
        ).onEach {
            Timber.e("뭐지 : $it")

            binding.rvHomeCommunity.scrollToPosition(0)
            if (it.size > 2) {
                communityMainContentAdapter.submitList(it.subList(0, 3))
            } else {
                communityMainContentAdapter.submitList(it)
            }

        }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    //navi control
    private fun naviControl() {
        binding.tvHomeReviewMore.setOnClickListener {
            homeViewModel.setHomeViewGA(1)
            findNavController().navigate(R.id.action_homeFragment_to_homeReviewFragment)
        }

        binding.tvHomeNewQuestionMore.setOnClickListener {
            homeViewModel.setHomeViewGA(2)
            findNavController().navigate(R.id.action_homeFragment_to_homeQuestionFragment)
        }

        binding.tvHomeRankingMore.setOnClickListener {
            homeViewModel.setHomeViewGA(4)
            findNavController().navigate(R.id.action_homeFragment_to_homeRankingFragment)
        }

        binding.tvHomeCommunityMore.setOnClickListener {
            homeViewModel.setHomeViewGA(3)
            mainViewModel.bottomNavItem.value = 8
        }
    }

    private fun setAnalytics() {
        homeViewModel.homeViewGA.observe(requireActivity()) {
            val paramValue = when (it) {
                1 -> "review_more"
                2 -> "question_1on1_more"
                3 -> "community_more"
                else -> "senior_more"
            }
            FirebaseAnalyticsUtil.firebaseLog("home_viewmore", "tap", paramValue)
        }

    }

    //시상대 data
    private fun setRanking() {
        homeViewModel.getHomeRanking(mainViewModel.univId.value ?: 0)
        homeViewModel.rankingData.observe(viewLifecycleOwner) {
            binding.apply {
                textNicknameRanking1.text = it[0].nickname
                textRateRanking1.text = "응답률 ${it[0].rate}%"
                if (it[0].rate == null) {
                    textRateRanking1.text = "응답률 --%"
                }
                imgRateRanking1.setImageResource(imageSelect(it[0].profileImageId))

                textNicknameRanking2.text = it[1].nickname
                textRateRanking2.text = "응답률 ${it[1].rate}%"
                if (it[1].rate == null) {
                    textRateRanking2.text = "응답률 --%"
                }
                imgRateRanking2.setImageResource(imageSelect(it[1].profileImageId))

                textNicknameRanking3.text = it[2].nickname
                textRateRanking3.text = "응답률 ${it[2].rate}%"
                if (it[2].rate == null) {
                    textRateRanking3.text = "응답률 --%"
                }
                imgRateRanking3.setImageResource(imageSelect(it[2].profileImageId))

                textNicknameRanking4.text = it[3].nickname
                textRateRanking4.text = "응답률 ${it[3].rate}%"
                if (it[3].rate == null) {
                    textRateRanking4.text = "응답률 --%"
                }
                imgRateRanking4.setImageResource(imageSelect(it[3].profileImageId))

                textNicknameRanking5.text = it[4].nickname
                textRateRanking5.text = "응답률 ${it[4].rate}%"
                if (it[4].rate == null) {
                    textRateRanking5.text = "응답률 --%"
                }
                imgRateRanking5.setImageResource(imageSelect(it[4].profileImageId))
            }

        }
    }

    //선배클릭 리스너
    private fun goSeniorPage() = with(binding) {
        val analytics = {
            FirebaseAnalyticsUtil.firebaseLog("senior_click", "journey", "senior_ranking_out")
        }
        homeViewModel.rankingData.observe(viewLifecycleOwner) {
            val firstId = it[0].id
            val secondId = it[1].id
            val thirdId = it[2].id
            val forthId = it[3].id
            val fifthId = it[4].id
            clRanking1.setOnClickListener {
                mainViewModel.seniorId.value = firstId
                goMyPage(firstId)
                analytics()
            }
            clRanking2.setOnClickListener {
                mainViewModel.seniorId.value = secondId
                goMyPage(secondId)
                analytics()
            }
            clRanking3.setOnClickListener {
                mainViewModel.seniorId.value = thirdId
                goMyPage(thirdId)
                analytics()
            }
            clRanking4.setOnClickListener {
                mainViewModel.seniorId.value = forthId
                goMyPage(forthId)
                analytics()
            }
            clRanking5.setOnClickListener {
                mainViewModel.seniorId.value = fifthId
                goMyPage(fifthId)
                analytics()
            }
        }
    }

    //선배 Id = userId가 같을 경우 마이페이지로 이동
    private fun goMyPage(seniorId: Int) {
        val userId = mainViewModel.userId.value ?: 0
        if (userId == seniorId) {
            mainViewModel.bottomNavItem.value = 4
        } else {
            mainViewModel.homeFragmentNum.value = 1
            mainViewModel.initLoading.value = true
        }
        mainViewModel.seniorBack.value = 0
    }

    //학교명
    private fun setUnivName() {
        if (mainViewModel.univId.value == 1) {
            binding.textHomeUnivName.text = "고려대학교"
        } else if (mainViewModel.univId.value == 2) {
            binding.textHomeUnivName.text = "서울여자대학교"
        } else {
            binding.textHomeUnivName.text = "중앙대학교"
        }
    }


}